package com.eurekakids.db.datamodel;

public class District {
	private int district_id;
	private String district_name;

	public District(int id, String name){
		district_id = id;
		district_name = name;
	}

	public District(){
	}


	public int getDistrictId() {
		return district_id;
	}
	public void setDistrictId(int districtId) {
		this.district_id = districtId;
	}
	public String getDistrictName() {
		return district_name;
	}
	public void setDistrictName(String districtName) {
		this.district_name = districtName;
	}	
}
