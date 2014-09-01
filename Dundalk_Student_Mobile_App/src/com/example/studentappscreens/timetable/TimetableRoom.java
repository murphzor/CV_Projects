package com.example.studentappscreens.timetable;

public class TimetableRoom 
{
	
	private String title;
	private String identifier;
	private String value;
	
	public TimetableRoom(String title, String identifier) {
		super();
		this.title = title;
		this.identifier = identifier;
	}
	
	public TimetableRoom() {

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
		return title;
	}
	
	
	
}
