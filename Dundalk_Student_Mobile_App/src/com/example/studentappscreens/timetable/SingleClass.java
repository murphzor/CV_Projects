package com.example.studentappscreens.timetable;

public class SingleClass 
{
	private int classID;
	private String startTime;
	private String endTime;
	private String weeks;
	private String classTitle;
	private String type;
	private String lecturer;
	private String roomNo;


	public SingleClass(String startTime, String endTime, String weeks,
			String classTitle, String type, String lecturer, String roomNo) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.weeks = weeks;
		this.classTitle = classTitle;
		this.type = type;
		this.lecturer = lecturer;
		this.roomNo = roomNo;
	}

	public SingleClass()
	{
		this.startTime = "";
		this.endTime = "";
		this.weeks = "";
		this.classTitle = "";
		this.type = "";
		this.lecturer = "";
		this.roomNo = "";
	}
	

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
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
	
	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
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

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

//	@Override
//	public String toString() {
//		return classTitle+"   "+"Start: "+ startTime+"    End: "+ endTime+"  "+"Lecturer: "+ lecturer+"     Room: "+ roomNo;
//	}

	@Override
	public String toString() {
		return classTitle+" "+startTime+" "+endTime+" "+lecturer+" "+roomNo;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingleClass other = (SingleClass) obj;
		if (classID != other.classID)
			return false;
		if (classTitle == null) {
			if (other.classTitle != null)
				return false;
		} else if (!classTitle.equals(other.classTitle))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (lecturer == null) {
			if (other.lecturer != null)
				return false;
		} else if (!lecturer.equals(other.lecturer))
			return false;
		if (roomNo == null) {
			if (other.roomNo != null)
				return false;
		} else if (!roomNo.equals(other.roomNo))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (weeks == null) {
			if (other.weeks != null)
				return false;
		} else if (!weeks.equals(other.weeks))
			return false;
		return true;
	}
	
	
	
	
}
