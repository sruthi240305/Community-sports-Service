package com.club.bean;

public class EventRegistration {
	private int registrationID;
	private String memberID; 
	 private String eventName ;
	 private String sportType ;
	 private java.sql.Date eventDate;
	 private String sessionSlot;
	 private String venue;
	 private java.sql.Date registrationDate ;
	 private String attendanceStatus ;
	 private String resultStatus;
	public int getRegistrationID() {
		return registrationID;
	}
	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public java.sql.Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(java.sql.Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getSessionSlot() {
		return sessionSlot;
	}
	public void setSessionSlot(String sessionSlot) {
		this.sessionSlot = sessionSlot;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public java.sql.Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(java.sql.Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	 
}
