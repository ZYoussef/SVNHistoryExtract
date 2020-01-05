package artifact;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SvnManager {

	Properties prop = new Properties();
	private String confPath = "src/main/resources/config.properties";
	private String login;
	private String password;
	private String version;
	private String releaseVersion;
	private String url;
	private String firstDay;
	private String lastDay;
	private String tag = "";
	private String result=""; 
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	private List<String> revisions = new ArrayList<String>();
	private List<String> commits = new ArrayList<String>();
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<String> revisions) {
		this.revisions = revisions;
	}

	public List<String> getCommits() {
		return commits;
	}

	public void setCommits(List<String> commits) {
		this.commits = commits;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SvnManager() {
		// add init();
		createEmailText();
	}
	public boolean init()
	{
		 try
	        {
	            FileInputStream fis = new FileInputStream(confPath);
	            prop.load(fis);
	            return true;
	        }
	        catch (Exception e)
	        {
	            System.out.println("Error loading file: config.properties");
	            return false;
	        }
	}

	public SvnManager(String login, String password, String version, String releaseVersion, String firstDay) {
		init();
		this.login = login;
		this.password = password;
		this.version = version;
		this.releaseVersion = releaseVersion;
		this.firstDay = firstDay;
		this.url= prop.getProperty(version);
	}

	public SvnManager(String login, String password, String version, String releaseVersion, String firstDay,
			String lastDay) {
		init();
		this.login = login;
		this.password = password;
		this.version = version;
		this.releaseVersion = releaseVersion;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.url= prop.getProperty(version);
	}

	public void getUrl() {
		url = prop.getProperty(version);
	}

	public void process() throws Exception {
		
	
		SVNURL svnUrl = SVNURL.parseURIDecoded(url);
		SVNRepository repository = null;
		repository = SVNRepositoryFactory.create(svnUrl);
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(login, password);
		repository.setAuthenticationManager(authManager);
		Collection logEntries = repository.log(new String[] { "" }, null, 0, -1, true, true);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yy");
		LocalDateTime now = LocalDateTime.now();
		for (Iterator entries = logEntries.iterator(); entries.hasNext();) {
			SVNLogEntry logEntry = (SVNLogEntry) entries.next();
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yy");
			Date firstDayDate = formatter1.parse(firstDay);
			Date lastDayDate = formatter1.parse(lastDay);
			if ((logEntry.getDate().after(firstDayDate) || logEntry.getDate().equals(firstDayDate))
					&& (logEntry.getDate().before(lastDayDate) || logEntry.getDate().equals(lastDayDate))) {
				revisions.add(String.valueOf(logEntry.getRevision()));
				commits.add(logEntry.getMessage());
				System.out.println("Révision : " + logEntry.getRevision() + "\t" + logEntry.getMessage());
			}
			if (logEntry.getMessage().contains(releaseVersion))
				tag += logEntry.getMessage();
		}
	}
	
	public void createEmailText()
	{
		appendData("Bonjour,");
		appendData("Vous trouverez ci-dessous le contenu de la livraison de la version " + version);
		appendData("Livraison " + version);
		appendData("La nouvelle version de l’IHM EPE" + version +" (" + releaseVersion +") est disponible sous le tag suivant : "+tag);
	}
	
	public void appendData(String data)
	{
		result+=data+"\n";

	}

}
