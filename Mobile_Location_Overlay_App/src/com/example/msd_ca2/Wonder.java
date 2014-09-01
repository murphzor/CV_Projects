package com.example.msd_ca2;


public class Wonder {

	private String title;
	private String location;
	private String coordinates;
	private String from;

	public Wonder() {

	}

	public Wonder(String title, String location, String coordinates, String from) {
		super();
		this.title = title;
		this.location = location;
		this.coordinates = coordinates;
		this.from = from;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
