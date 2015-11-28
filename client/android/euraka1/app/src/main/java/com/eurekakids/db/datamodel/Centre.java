package com.eurekakids.db.datamodel;

public class Centre {
	private int village_id;
	private int centre_id;
	private String centre_name;

    public Centre(int village_id, int centre_id, String centre_name){
        this.village_id = village_id;
        this.centre_id = centre_id;
        this.centre_name = centre_name;
    }

    public Centre(){}
	
	public int getVillageId() {
		return village_id;
	}
	public void setVillageId(int villageId) {
		this.village_id = villageId;
	}
	public int getCentreId() {
		return centre_id;
	}
	public void setCentreId(int centreId) {
		this.centre_id = centreId;
	}
	public String getCentreName() {
		return centre_name;
	}
	public void setCentreName(String centreName) {
		this.centre_name = centreName;
	}
}
