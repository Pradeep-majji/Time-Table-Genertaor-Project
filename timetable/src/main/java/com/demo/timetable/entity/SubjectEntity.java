package com.demo.timetable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="subjects")
@Entity
public class SubjectEntity {

	@Id
	@Column(name="sid")
	private String sid;
	@Column(name="sname")
	private String sname;
	@Column(name="stype")
	private String stype;
	@Column(name="sclass")
	private String sclass;
	
	public SubjectEntity() {}
	
	public SubjectEntity(String sid, String sname, String stype, String sclass) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.stype = stype;
		this.sclass = sclass;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getSclass() {
		return sclass;
	}

	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
	
	
	
}
