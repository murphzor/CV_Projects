package com.example.studentappscreens.timetable;

import java.util.ArrayList;

public class FullTimetable 
{
	private long id;
	private String title;
	private String monClasses;
	private String tuesClasses;
	private String wedClasses;
	private String thurClasses;
	private String friClasses;
	
	
	public FullTimetable(String title, String monClasses, String tuesClasses,
			String wedClasses, String thurClasses, String friClasses) {
		super();
		this.title = title;
		this.monClasses = monClasses;
		this.tuesClasses = tuesClasses;
		this.wedClasses = wedClasses;
		this.thurClasses = thurClasses;
		this.friClasses = friClasses;
	}
	public FullTimetable()
	{
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMonClasses() {
		return monClasses;
	}
	public void setMonClasses(String monClasses) {
		this.monClasses = monClasses;
	}
	public String getTuesClasses() {
		return tuesClasses;
	}
	public void setTuesClasses(String tuesClasses) {
		this.tuesClasses = tuesClasses;
	}
	public String getWedClasses() {
		return wedClasses;
	}
	public void setWedClasses(String wedClasses) {
		this.wedClasses = wedClasses;
	}
	public String getThurClasses() {
		return thurClasses;
	}
	public void setThurClasses(String thurClasses) {
		this.thurClasses = thurClasses;
	}
	public String getFriClasses() {
		return friClasses;
	}
	public void setFriClasses(String friClasses) {
		this.friClasses = friClasses;
	}
	@Override
	public String toString() {
		return title;
	}
	
	
}
