package com.example.studentappscreens.timetable;

public class Zone 
{
	private String name;
	private String identifier;
	
	public Zone(String name, String identifier) {
		super();
		this.name = name;
		this.identifier = identifier;
	}
	
	public Zone()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
}
