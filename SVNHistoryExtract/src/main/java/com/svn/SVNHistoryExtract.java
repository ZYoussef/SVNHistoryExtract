package com.svn;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNHistoryExtract
{

    /**
     * @param args
     */
    static SVNClientManager ourClientManager;

    public static void main(String[] args)
        throws Exception
    {

        String username = "";
        String password = "";
        Properties prop = new Properties();
        String confPath = "src/main/resources/config.properties";
        try
        {
            FileInputStream fis = new FileInputStream(confPath);
            prop.load(fis);
        }
        catch (Exception e)
        {
            System.out.println("Error loading file: config.properties");
        }
        username = prop.getProperty("username");
        password = prop.getProperty("password");
        boolean accessOk = false;

        String loc = JOptionPane.showInputDialog("Enter SVN Location");
        String sender = JOptionPane.showInputDialog("Enter sender's name");
        String version = JOptionPane.showInputDialog("Enter version name");
        String releaseVersion = JOptionPane.showInputDialog("Enter release version");
        String firstDay = JOptionPane.showInputDialog("Enter first day  of history (dd/mm/yy)");
        String lastDay = JOptionPane.showInputDialog("Enter last  day  of history (dd/mm/yy)");

        List<String> revisions = new ArrayList<String>();
        List<String> commits = new ArrayList<String>();
        SVNURL url = SVNURL.parseURIDecoded(loc);
        SVNRepository repository = null;
        repository = SVNRepositoryFactory.create(url);
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
        Collection logEntries = repository.log(new String[] { "" }, null, 0, -1, true, true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yy");
        LocalDateTime now = LocalDateTime.now();
        PrintWriter writer =
            new PrintWriter("livraison_" + version + "_" + String.valueOf(dtf.format(now)) + ".txt", "UTF-8");
        writer.println("Bonjour,\n");
        writer.println("Vous trouverez ci-dessous le contenu de la livraison de la version " + version);
        writer.println("Livraison " + version + "// à mettre en gras\n");
        writer.println("La nouvelle version de l’IHM EPE " + version + " (" + releaseVersion
            + ") est disponible sous le tag suivant : \n");

        for (Iterator entries = logEntries.iterator(); entries.hasNext();)
        {
            SVNLogEntry logEntry = (SVNLogEntry) entries.next();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
            Date dateRefDebut = formatter1.parse(firstDay);
            Date dateRefFin = formatter1.parse(lastDay);
            if ((logEntry.getDate().after(dateRefDebut) || logEntry.getDate().equals(dateRefDebut))
                && (logEntry.getDate().before(dateRefFin) || logEntry.getDate().equals(dateRefFin)))
            {
                revisions.add(String.valueOf(logEntry.getRevision()));
                commits.add(logEntry.getMessage());
                System.out.println("Révision : " + logEntry.getRevision() + "\t" + logEntry.getMessage());
            }
        }

        String allRev = "";
        for (String str : revisions)
        {
            allRev += str + " ";
        }
        writer.println("Révisions : " + allRev);
        writer.println("");
        writer.println("La livraison contient les correctifs suivants :");
        writer.println("");
        for (String str : commits)
        {
            if (str.contains("[DIVERS]"))
            {
                writer.println("[Technologie : Java] " + str);
            }
            else
            {
                writer.println("[Technologie : Java][DALIXXX_IRM-XXX] " + str);
            }
        }
        writer.println("");
        writer.println("La livraison contient les correctifs de la version précedente suivants :\n");
        writer.println(sender);
        writer.close();
        JOptionPane.showMessageDialog(null, "Done");
    }
}
