package com.eurekakids.db.datamodel;

public class Village {
	private int block_id;
	private int village_id;
	private String village_name;


    public Village(int block_id, int village_id, String village_name){
        this.block_id=block_id;
        this.village_id=village_id;
        this.village_name=village_name;
    }

    public Village(){

    }


    public int getBlock_id() {
		return block_id;
	}
	public void setBlock_id(int block_id) {
		this.block_id = block_id;
	}
	public int getVillage_id() {
		return village_id;
	}
	public void setVillage_id(int village_id) {
		this.village_id = village_id;
	}
	public String getVillage_name() {
		return village_name;
	}
	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}
}
