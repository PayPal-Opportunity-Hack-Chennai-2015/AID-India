package com.eurekakids.db.datamodel;

public class Block {
	private int district_id;
	private int block_id;
	private String block_name;
    public Block(int district_id, int block_id, String block_name){
        this.block_id=block_id;
        this.district_id=district_id;
        this.block_name=block_name;
    }
    public Block(){

    }
	public int getDistrictId() {
		return district_id;
	}
	public void setDistrictId(int districtId) {
		this.district_id = districtId;
	}
	public int getBlockId() {
		return block_id;
	}
	public void setBlockId(int blockId) {
		this.block_id = blockId;
	}
	public String getBlockName() {
		return block_name;
	}
	public void setBlockName(String blockName) {
		this.block_name = blockName;
	}
}
