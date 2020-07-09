package bo;

import utils.Constants;

public class Author implements Constants{
	private String name;
	private String url;
	private String icon;
	
	public Author(String name, String url, String icon){
		this.name = name;
		this.url = url;
		this.icon = icon;
	}
	
	public Author(){
		this.name = "";
		this.url = "";
		this.icon = "";
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Override
	public String toString() {
		return 	SPACE
				+TAG_AUTHOR
				+ NEW_LINE
				+ TAB
				+ TAB
				+ TAB
				+ TAG_AUTHOR_NAME
				+ EQUALS
				+ this.name
				+ NEW_LINE
				+ TAB
				+ TAB
				+ TAB
				+ TAG_AUTHOR_URL
				+ EQUALS
				+ this.url 
				+ NEW_LINE
				+ TAB
				+ TAB
				+ TAB
				+ TAG_AUTHOR_ICON
				+ EQUALS
				+ this.icon
				+  NEW_LINE;
	}
}
