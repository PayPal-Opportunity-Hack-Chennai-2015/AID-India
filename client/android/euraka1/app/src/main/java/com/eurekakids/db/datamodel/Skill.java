package com.eurekakids.db.datamodel;

public class Skill {
	private int skill_id;
	private String skill_name;
	private String subject_name;

    public Skill(int skill_id, String skill_name, String subject_name) {
        this.skill_id = skill_id;
        this.skill_name = skill_name;
        this.subject_name = subject_name;
    }

    public Skill(){}

    public int getSkillId() {
		return skill_id;
	}
	public void setSkillId(int skillId) {
		this.skill_id = skillId;
	}
	public String getSkillName() {
		return skill_name;
	}
	public void setSkillName(String skillName) {
		this.skill_name = skillName;
	}
	public String getSubjectName() {
		return subject_name;
	}
	public void setSubjectName(String subjectName) {
		this.subject_name = subjectName;
	}
}
