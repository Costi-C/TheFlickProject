package test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import bo.Feed;
import utils.ReadAndWriteUtils;

public class ReadFeedFileTest {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException{
		Feed fd = null;
		ReadAndWriteUtils rawu = new ReadAndWriteUtils();
		String filePath = "input.xml";		
		fd = rawu.readData(filePath);
		System.out.println(rawu.feedToString(fd));
//		System.out.println(fd.toString());
		//rawu.feedToString(fd);
		//s = rawu.feedToDOM(fd);
		//xml = rawu.feedToXMLString(fd);
	}
}
