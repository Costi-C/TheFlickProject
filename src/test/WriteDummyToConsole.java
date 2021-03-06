package test;

import java.util.Date;
import java.util.Vector;

import utils.ReadAndWriteUtils;
import bo.Author;
import bo.Feed;
import bo.Image;

public class WriteDummyToConsole {
	public static void main(String [] args){
		Feed fd = null;
		String title = "asdasTitle";
		Date d = new Date();
		Vector<Image> vec = new Vector<Image>();
		String returnObject = "";
		
		String authName = "Otilia";
		String url = "https://www.flickr.com/people/135392323@N03/";
		String icon = "https://s.yimg.com/pw/images/buddyicon11.png";
		Author auth1 = new Author(authName, url, icon);
		Image img1 = new Image();
		img1.setTitle("Screenshot_2015-09-20-19-41-35");
		img1.setAuthor(auth1);
		img1.setImageDate(d);
		vec.add(img1);
		fd = new Feed();
		fd.setTitle(title);
		fd.setFeedDate(d);
		fd.setFeedData(vec);
		
		ReadAndWriteUtils rawu = new ReadAndWriteUtils();
		returnObject = rawu.feedToString(fd);
		System.out.println(returnObject.toString());
	}
}
