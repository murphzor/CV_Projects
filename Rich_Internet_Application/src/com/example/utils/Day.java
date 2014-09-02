package com.example.utils;
import java.io.Serializable;
import java.util.ArrayList;

import com.example.ca.client.SingleClass;


public class Day implements Serializable 
{
	private String day;
	private ArrayList<SingleClass> classes = new ArrayList<SingleClass>();
	
	public Day(String day)
	{
		this.day = day;
	}
	
	public Day()
	{

	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public ArrayList<SingleClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SingleClass> classes) {
		this.classes = classes;
	}
	
}
