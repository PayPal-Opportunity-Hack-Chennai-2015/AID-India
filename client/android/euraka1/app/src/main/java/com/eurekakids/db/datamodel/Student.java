package com.eurekakids.db.datamodel;

public class Student {
	private int centre_id;
	private int student_id;
	private String student_name;
	private int student_std;
	
	public int getCentreId() {
		return centre_id;
	}
	public void setCentreId(int centreId) {
		this.centre_id = centreId;
	}
	public int getStudentId() {
		return student_id;
	}
	public void setStudentId(int studentId) {
		this.student_id = studentId;
	}
	public String getStudentName() {
		return student_name;
	}
	public void setStudentName(String studentName) {
		this.student_name = studentName;
	}
	public int getStudentStd() {
		return student_std;
	}
	public void setStudentStd(int studentStd) {
		this.student_std = studentStd;
	}
	
}
