package com.example.studentappscreens.timetable;

public class SingleRoom {
	private int roomID;
	private String startTime;
	private String endTime;
	private String weeks;
	private String classTitle;
	private String type;
	private String lecturer;
	private String groups;
	
	public SingleRoom(int roomID, String startTime, String endTime,
			String weeks, String classTitle, String type, String lecturer,
			String groups) {
		super();
		this.roomID = roomID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.weeks = weeks;
		this.classTitle = classTitle;
		this.type = type;
		this.lecturer = lecturer;
		this.groups = groups;
	}

	public SingleRoom()
	{
		
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return classTitle+"   "+"Start: "+ startTime+"    End: "+ endTime+"  "+"Lecturer: "+ lecturer+"     Groups: "+ groups;
	}
	
}
