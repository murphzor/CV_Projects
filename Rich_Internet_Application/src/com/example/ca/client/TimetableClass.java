package com.example.ca.client;

import java.io.Serializable;

public class TimetableClass implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String identifier;
	private String value;
	
	public TimetableClass()
	{
		
	}

	public TimetableClass(String title, String identifier, String value)
	{
		super();
		this.title = title;
		this.identifier = identifier;
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TimetableClass [title=" + title + ", identifier=" + identifier
				+ ", value=" + value + "]";
	}
	
	
	

}
