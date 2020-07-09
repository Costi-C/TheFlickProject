package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import db.DBConnection;
import bo.Feed;
import utils.Constants;
import utils.ReadAndWriteUtils;

public class ProcessMultipleInputFiles {
	static Logger log = Logger.getLogger(ProcessMultipleInputFiles.class.getName());
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		File raport = new File("raport.txt");
		FileWriter fw = new FileWriter(raport);
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		String fileName = Constants.CONFIG_PROPERTIES_FILE;
		try {

			input = new FileInputStream(fileName);
			if (input != null) {
				prop.load(input);
			}

			String folderPath = prop
					.getProperty(Constants.PROPERTY_FOLDER_PATH);
			File f = new File(folderPath);
			int count = 0;
			int invalidFiles = 0;
			
			for (File fileEntry : f.listFiles()) {
				if (fileEntry.isFile() && fileEntry.getName().endsWith(".xml")) {
					count++;
					ReadAndWriteUtils rawu = new ReadAndWriteUtils();
					try {
						Feed fd = rawu.readData(fileEntry.getName());
						if (fd == null) {						
							bw.write(Integer.toString(count) + Constants.DOT
									+ Constants.SPACE + fileEntry.getName()
									+ "(" + " - " + ")");
						} else if(fd.getFeedData().size() == 0) {
							bw.write(Integer.toString(count) + Constants.DOT
									+ Constants.SPACE + fileEntry.getName()
									+ "(" + "no image" + ")"
									+ "VALID"
									+ Constants.NEW_LINE);
						} else {
							log.info("Trying to insert into database!!");
							DBConnection.insertRecords(fd);
							bw.write(Integer.toString(count) + Constants.DOT
									+ Constants.SPACE + fileEntry.getName()
									+ " (" + fd.getFeedData().size()
									+ " images)" + " VALID "
									+ Constants.NEW_LINE);
							//System.out.println(fd.getFeedData().size());
							
						}
					} catch (Exception ex) {
						bw.write(Integer.toString(count) + Constants.DOT
								+ Constants.SPACE + fileEntry.getName()
								+ "(" + " - " + ")" + " INVALID" + Constants.NEW_LINE
								);
						invalidFiles++;
					}					
				}

			}
			bw.write(Constants.NEW_LINE);
			bw.write("Number of files to be processed: " + count + Constants.NEW_LINE);
			
			int validFiles = 0;
			validFiles = count-invalidFiles;			
			bw.write("Result: " + validFiles +"/" + count + Constants.NEW_LINE);			
			
			int percent = (validFiles * 100) / count;	
			bw.write("Validity:" + Integer.toString(percent) + "%");
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (input != null && bw != null && fw != null) {
				try {
					input.close();
					bw.close();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
