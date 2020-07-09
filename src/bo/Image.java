package bo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.Constants;

public class Image implements Constants {
	private String title;
	private String url;
	private Author author;
	private Date imageDate;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	

	public Image() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Date getImageDate() {
		return imageDate;
	}

	public void setImageDate(Date imageDate) {
		this.imageDate = imageDate;
	}

	@Override
	public String toString() {
		return  TAB 
				+ TAG_IMAGE 
				+ NEW_LINE
				+ TAB
				+ TAB
				+ TAG_TITLE
				+ this.title
				+ NEW_LINE
				+ TAB
				+ TAB
				+ SPACE
				+ URL
				+ EQUALS				
				+ url
				+ NEW_LINE
				+ TAB
				+ TAB
				+ author.toString()				
				+ TAB
				+ TAB
				+ IMAGE_DATE
				+ df.format(imageDate)
				+NEW_LINE
				+NEW_LINE;
				
	}
}
