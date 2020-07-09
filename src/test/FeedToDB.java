package test;

import java.text.ParseException;
import java.util.Date;
import java.util.Vector;

import bo.Author;
import bo.Feed;
import bo.Image;
import db.DBConnection;

public class FeedToDB {
	public static void main(String[] args) throws ParseException {
		DBConnection dbConn = new DBConnection();
		DBConnection.connect();
		Feed fd = new Feed();
		Vector<Image> vector = new Vector<Image>();
		Date date = new Date();
		
		String authName = "Otilia";
		String authUrl = "https://www.flickr.com/people/135392323@N03/";
		String authIcon = "https://s.yimg.com/pw/images/buddyicon11.png";
		Author auth = new Author(authName, authUrl, authIcon);		
	
		Image img = new Image();		
		img.setTitle("Prepared Statement");		
		img.setUrl("https://asdasa.ro/sda");
		img.setImageDate(date);
		img.setAuthor(auth);
		vector.addElement(img);
		fd.setFeedData(vector);	
		DBConnection.insertRecords(fd);		
		//DBConnection.updateRecords(fd,"305750 Number", "wiakia_com", 24);
		//DBConnection.deleteRecords(29);
		//DBConnection.selectedRecords(24);
		

	}
}
