package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bo.Feed;
import bo.Image;
import parser.FlickFeedParser;

public class ReadAndWriteUtils implements Constants {
	public Feed readData(String fileName) throws IOException,
			ParserConfigurationException, SAXException {
		FlickFeedParser ffp = new FlickFeedParser();
		String text = new String(Files.readAllBytes(Paths.get(fileName)),
				StandardCharsets.UTF_8);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		sp.parse(new InputSource(new StringReader(text)), ffp);
		return ffp.getData();
	}

	public String feedToString(Feed fd) {
		String content = "";
		String file_date = "";
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		file_date = df.format(d);
		Vector<Image> vector = null;
		vector = fd.getFeedData();
		content += fd.toString();

		for (Image image : vector) {
			content += image.toString();
		}

		return content;
	}

	public String feedToDOM(Feed feedData) throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.newDocument();

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date d = new Date();
		int totalNumber = feedData.getFeedData().size();
		String file_date = df.format(d);
		String root = feedData.getTitle();
		Date feedDate = feedData.getFeedDate();
		String feedDateFormat = df.format(feedDate);

		Element rootElem = doc.createElement(TAG_FEED);
		doc.appendChild(rootElem);
		rootElem.setAttribute(TITLE, root);
		rootElem.setAttribute(CURRENT_DATE, file_date);
		rootElem.setAttribute(FEED_DATE, feedDateFormat);
		rootElem.setAttribute(NUMBER_OF_IMAGES, Integer.toString(totalNumber));

		Vector<Image> vector = null;
		vector = feedData.getFeedData();

		for (Image image : vector) {

			Element img = doc.createElement(TAG_IMAGE);
			String imgTitle = image.getTitle();
			img.setAttribute(TITLE, imgTitle);
			rootElem.appendChild(img);

			Element urlImage = doc.createElement(URL);
			String imgUrl = image.getUrl();
			urlImage.setTextContent(imgUrl);
			img.appendChild(urlImage);

			Element authorElement = doc.createElement(TAG_AUTHOR);
			img.appendChild(authorElement);

			Element authorName = doc.createElement(TAG_AUTHOR_NAME);
			String name = image.getAuthor().getName();
			authorName.setTextContent(name);
			authorElement.appendChild(authorName);

			Element authorUrl = doc.createElement(TAG_AUTHOR_URL);
			String url = image.getAuthor().getUrl();
			authorUrl.setTextContent(url);
			authorElement.appendChild(authorUrl);

			Element authorIcon = doc.createElement(TAG_AUTHOR_ICON);
			String icon = image.getAuthor().getIcon();
			authorIcon.setTextContent(icon);
			authorElement.appendChild(authorIcon);
		}

		StringWriter sw = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1254");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "5");
		Source source = new DOMSource(doc);
		Result output = new StreamResult(sw);
		transformer.transform(source, output);
		return sw.toString();
	}

	public String feedToXMLString(Feed feedData)
			throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {
		return feedToDOM(feedData);
	}

	public static void writeDataToFile(String data, String outputFile) {
		File f = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			f = new File(outputFile);
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			bw.write(data);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (bw != null & fw != null) {
				try {
					bw.close();
					fw.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

}
