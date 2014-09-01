package com.example.studentappscreens.timetable;
import java.util.ArrayList;


public class Day 
{
	private String day;
	private ArrayList<SingleClass> classes = new ArrayList<SingleClass>();
	private ArrayList<SingleRoom> rooms = new ArrayList<SingleRoom>();
	
	public Day(String day)
	{
		this.day = day;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public ArrayList<SingleRoom> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<SingleRoom> rooms) {
		this.rooms = rooms;
	}

	public ArrayList<SingleClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SingleClass> classes) {
		this.classes = classes;
	}

//	@Override
//	public String toString() {
//		return "Day [day=" + day + ", classes=" + classes + "]";
//	}
	
	@Override
	public String toString() {
		return day;
	}
	
	
}
