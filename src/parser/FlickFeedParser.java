package parser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import javax.management.relation.Relation;
import javax.security.sasl.SaslException;
import javax.swing.text.html.parser.ParserDelegator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import utils.Constants;
import bo.Author;
import bo.Feed;
import bo.Image;

public class FlickFeedParser extends DefaultHandler implements Constants {

	private Vector<Image> feedData = null;

	private Feed fd = null;
	private Image img = null;
	private Author author = null;

	boolean isFeed;
	boolean isEntry;
	boolean isEntryUrl;
	boolean isRelAlternate;
	boolean isTitle;
	boolean isAuthor;
	boolean isName;
	boolean isUrl;
	boolean isIcon;
	boolean isDate;

	String rel = null;
	String href = null;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase(TAG_FEED)) {
			isFeed = true;
			fd = new Feed();
			feedData = new Vector<Image>();

		}

		if (qName.equalsIgnoreCase(ENTRY)) {
			isEntry = true;			
			img = new Image();			
		}

		if (qName.equalsIgnoreCase(TAG_IMAGE_URL)) {
			rel = attributes.getValue(TAG_IMAGE_REL_ATTR);
			href = attributes.getValue(TAG_IMAGE_HREF_ATTR);
			if (isEntry && rel.equalsIgnoreCase(TAG_IMAGE_REL_ATTR_VALUE)) {
				isRelAlternate = true;				
			} else if(isEntry && rel.equalsIgnoreCase("enclosure")){
				img.setUrl(href);
			} else {
				isRelAlternate = false;
			}
			isEntryUrl = true;
		}

		if (qName.equalsIgnoreCase(TITLE)) {
			isTitle = true;
		}

		if (qName.equalsIgnoreCase(UPDATED)) {
			isDate = true;
		}

		if (qName.equalsIgnoreCase(TAG_AUTHOR)) {
			author = new Author();
			isAuthor = true;
		}
		if (qName.equalsIgnoreCase(NAME)) {
			isName = true;
		}

		if (qName.equalsIgnoreCase(URI)) {
			isUrl = true;
		}

		if (qName.equalsIgnoreCase(FLICKR_BUDDY_ICON)) {
			isIcon = true;
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase(TAG_FEED)) {
			isFeed = false;
		} else if (qName.equalsIgnoreCase(TITLE)) {
			isTitle = false;
		} else if (qName.equalsIgnoreCase(ENTRY)) {							
			feedData.add(img);			
			fd.setFeedData(feedData);			
			isEntry = false;
		} else if (qName.equalsIgnoreCase(TAG_IMAGE_URL)) {
			isRelAlternate = false;
			isEntryUrl = false;
		} else if (qName.equalsIgnoreCase(UPDATED)) {
			isDate = false;
		} else if (qName.equalsIgnoreCase(TAG_AUTHOR)) {			
			img.setAuthor(author);			
			isAuthor = false;
		} else if (qName.equalsIgnoreCase(NAME)) {
			isName = false;
		} else if (qName.equalsIgnoreCase(URI)) {
			isUrl = false;
		} else if (qName.equalsIgnoreCase(FLICKR_BUDDY_ICON)) {
			isIcon = false;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (isFeed) {
			if (isEntry) {
				if (isTitle) {
					img.setTitle(new String(ch, start, length));
					isTitle = false;
				}
				if (isEntryUrl) {
					isEntryUrl = false;
				}
				if (isDate) {
					String entryDate = new String(ch, start, length);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss'Z'");
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date parseDate1 = null;
					try {
						parseDate1 = formatter.parse(entryDate);
						formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
						String formattedStr = formatter.format(parseDate1);
						parseDate1 = formatter.parse(formattedStr);
						img.setImageDate(parseDate1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isDate = false;
				}
				if (isAuthor) {
					isAuthor = false;
				}

				if (isName) {
					author.setName(new String(ch, start, length));
					isName = false;
				}
				if (isUrl) {
					author.setUrl(new String(ch, start, length));
					isUrl = false;
				}
				if (isIcon) {
					author.setIcon(new String(ch, start, length));
					isIcon = false;
				}

			} else {
				if (isTitle) {
					fd.setTitle(new String(ch, start, length));
					isTitle = false;
				}

				if (isDate) {
					String feedDate = new String(ch, start, length);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss'Z'");
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date parseDate = null;
					try {
						parseDate = formatter.parse(feedDate);
						formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
						String formattedStr = formatter.format(parseDate);
						parseDate = formatter.parse(formattedStr);
						fd.setFeedDate(parseDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isDate = false;
				}

			}

		}

	}

	public Feed getData() {
		return fd;
	}
}