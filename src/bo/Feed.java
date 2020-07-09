package bo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import utils.Constants;

public class Feed implements Constants {
	private String title;
	private Date feedDate;
	private Vector<Image> feedData;	
	private Date fileDate = new Date();
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String formatFileDate = df.format(fileDate);

	public Feed() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFeedDate() {
		return feedDate;
	}

	public void setFeedDate(Date feedDate) {
		this.feedDate = feedDate;
	}

	public Vector<Image> getFeedData() {
		return feedData;
	}

	public void setFeedData(Vector<Image> feedData) {
		this.feedData = feedData;
	}

	@Override
	public String toString() {
		return TAG_FEED + NEW_LINE + TAB + TAG_TITLE + this.title + NEW_LINE
				+ TAB + SPACE + CURRENT_DATE + EQUALS + formatFileDate
				+ NEW_LINE + TAB + SPACE + FEED_DATE + EQUALS
				+ df.format(feedDate) + NEW_LINE + TAB + SPACE
				+ NUMBER_OF_IMAGES + EQUALS + feedData.size() + NEW_LINE + NEW_LINE;
	}
}
