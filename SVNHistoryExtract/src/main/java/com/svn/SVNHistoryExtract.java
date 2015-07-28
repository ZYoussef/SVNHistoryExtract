package com.svn;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

public class SVNHistoryExtract {

	/**
	 * @param args
	 */
	static SVNClientManager ourClientManager;

public static void main(String[] args)throws Exception {
	
	String username="";
	String password="";
	Properties prop=new Properties();
	String confPath="src/main/resources/config.properties";
	try{
		FileInputStream fis=new FileInputStream(confPath);
		prop.load(fis);
		}catch(Exception e){System.out.println("Error loading file: config.properties");}
		username=prop.getProperty("username");
		password=prop.getProperty("password");
	String sheetName="logs";
	String loc=JOptionPane.showInputDialog("Enter SVN Location");
	String fileName=JOptionPane.showInputDialog("Enter Output FileName with .xls extension");
	SVNURL url = SVNURL.parseURIDecoded( loc);
	
	File f=new File(fileName);
	f.createNewFile();
	WorkbookSettings wbSettings = new WorkbookSettings();
    wbSettings.setLocale(new Locale("en", "EN"));
	WritableWorkbook copy=Workbook.createWorkbook(f, wbSettings);
	
	WritableSheet sheetN = copy.createSheet(sheetName,0 );
	int row=0;
	int col=0;
	WritableCell cell = sheetN.getWritableCell(1, 2);
	Label label1 = new Label(col, row, "revision");
	col++;
	Label label2 = new Label(col, row, "author");
	col++;
	Label label3 = new Label(col, row, "date");
	col++;
	Label label4 = new Label(col, row, "log message");
	col++;
	Label label5 = new Label(col, row, "changed paths:");
	col++;
	sheetN.addCell(label1);
	sheetN.addCell(label2);
	sheetN.addCell(label3);
	sheetN.addCell(label4);
	sheetN.addCell(label5);
	
	SVNRepository repository = SVNRepositoryFactory.create(url);
	ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
	repository.setAuthenticationManager(authManager);
	Collection logEntries = repository.log(new String[] { "" }, null, 0, -1, true, true);
	//System.out.println(logEntries);
	int sheetnum=1;
	for ( Iterator entries = logEntries.iterator( ); entries.hasNext( ); ) {
        row++;
        col=0;
        if(row>60000)
        { 
        	sheetN = copy.createSheet(sheetName+sheetnum,sheetnum );
        	row=1;
        }
		SVNLogEntry logEntry = ( SVNLogEntry ) entries.next( );
        //System.out.println( "---------------------------------------------" );
        //System.out.println ("revision: " + logEntry.getRevision( ) );
        Label ilabel1 = new Label(col, row, logEntry.getRevision( )+"");
        col++;
        //System.out.println( "author: " + logEntry.getAuthor( ) );
        Label ilabel2 = new Label(col, row, logEntry.getAuthor( )+"");
        col++;
        //System.out.println( "date: " + logEntry.getDate( ) );
        Label ilabel3= new Label(col, row, logEntry.getDate( )+"");
        col++;
        //System.out.println( "log message: " + logEntry.getMessage( ) );
        Label ilabel4= new Label(col, row, logEntry.getMessage( )+"");
        col++;
        String chngd="";
        if ( logEntry.getChangedPaths( ).size( ) > 0 ) {
            //System.out.println( );
            //System.out.println( "changed paths:" );
        	chngd="";
            Set changedPathsSet = logEntry.getChangedPaths( ).keySet( );

            for ( Iterator changedPaths = changedPathsSet.iterator( ); changedPaths.hasNext( ); ) {
                SVNLogEntryPath entryPath = ( SVNLogEntryPath ) logEntry.getChangedPaths( ).get( changedPaths.next( ) );
                
                if(!(entryPath.getPath().contains("1769")))
                	continue;
                
                chngd=( " "
                        + entryPath.getType( )
                        + " "
                        + entryPath.getPath( )
                        + ( ( entryPath.getCopyPath( ) != null ) ? " (from "
                                + entryPath.getCopyPath( ) + " revision "
                                + entryPath.getCopyRevision( ) + ")" : "" ) +"\n");
                Label ilabel5= new Label(col, row, chngd);
                sheetN.addCell(ilabel5);
                row++;
            }
            
        }
        //Label ilabel5= new Label(col, row, chngd);
        sheetN.addCell(ilabel1);
    	sheetN.addCell(ilabel2);
    	sheetN.addCell(ilabel3);
    	sheetN.addCell(ilabel4);
    	//sheetN.addCell(ilabel5);
    	
    }
	copy.write();
	copy.close();
	//workbook.close();
//System.out.println("done");
	JOptionPane.showMessageDialog(null, "Done");
}
}
