package com.example.studentappscreens.timetable;

public class DepartmentFilter 
{
	private String title;
	private String filter;
	public DepartmentFilter(String title, String filter) {
		super();
		this.title = title;
		this.filter = filter;
	}
	
	public DepartmentFilter() 
	{

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	

	
}
