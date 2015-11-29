package com.eurekakids.db.datamodel;

import java.io.Serializable;

public class Student implements Serializable {
	private int centre_id;
	private int student_id;
	private String student_name;
	private int student_std;

	public Student(int c_id, String s_name, int s_std){
		this.centre_id = c_id;
		this.student_name = s_name;
		this.student_std = s_std;
	}

	public Student(){}

	@Override
	public String toString() {
		return student_name;
	}

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
