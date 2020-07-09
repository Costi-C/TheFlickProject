package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import db.DBConnection;
import utils.Constants;
import utils.ReadAndWriteUtils;
import bo.Feed;

public class RunApplication {
	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, TransformerFactoryConfigurationError,
			TransformerException, ParseException {

		InputStream input = null;
		Feed fd = null;
		String returnTxt = "";
		String returnXml = "";
		ReadAndWriteUtils rawu = new ReadAndWriteUtils();
		try {
			Properties prop = new Properties();
			String fileName = Constants.CONFIG_PROPERTIES_FILE;
			input = new FileInputStream(fileName);
			if (input != null) {
				prop.load(input);
			} else {
				throw new FileNotFoundException("Configuration file "
						+ fileName + " not found!!!");
			}

			String inputFile = prop.getProperty(Constants.PROPERTY_INPUT_TXT,
					Constants.PROPERTY_DEF_INPUT_TXT);
			File f = new File(inputFile);
			String outTxtFile = prop.getProperty(
					Constants.PROPERTY_OUTPUT_TEXT,
					Constants.PROPERTY_DEF_OUTPUT_TEXT);
			String outXmlFile = prop.getProperty(Constants.PROPERTY_OUTPUT_XML,
					Constants.PROPERTY_DEF_OUTPUT_XML);
			Boolean isProcAndDel = Boolean.valueOf(prop.getProperty(
					Constants.PROPERTY_PROCESS_AND_DELETE,
					Constants.PROPERTY_DEF_PROCESS_AND_DELETE));
			Boolean isSaveToDB = Boolean.valueOf(prop.getProperty(
					Constants.PROPERTY_SAVE_TO_DATABASE,
					Constants.PROPERTY_DEF_SAVE_TO_DATABASE));

			fd = rawu.readData(inputFile);
			if (isSaveToDB) {
				DBConnection.insertRecords(fd);
			}

			returnTxt = rawu.feedToString(fd);
			rawu.writeDataToFile(returnTxt, outTxtFile);

			returnXml = rawu.feedToXMLString(fd);
			rawu.writeDataToFile(returnXml, outXmlFile);

			if (isProcAndDel) {
				f.delete();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
