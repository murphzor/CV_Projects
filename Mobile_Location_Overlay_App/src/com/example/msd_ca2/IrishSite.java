package com.example.msd_ca2;

public class IrishSite 
{

	private String title;
	private String coordinates;
	private String info;

	public IrishSite() {

	}

	public IrishSite(String title, String coordinates, String info)
	{
		super();
		this.title = title;
		this.coordinates = coordinates;
		this.info = info;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}

