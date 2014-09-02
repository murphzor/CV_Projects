package com.example.ca.client;

import java.io.Serializable;

public class NewsEntry implements Serializable 
{
	private String title;
	private String link;
	
	public NewsEntry(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}

	public NewsEntry() {

	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	
}
