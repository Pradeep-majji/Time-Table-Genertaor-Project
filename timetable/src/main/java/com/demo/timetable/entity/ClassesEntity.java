package com.demo.timetable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="classes")
@Entity
public class ClassesEntity {

	@Id
	@Column(name="cid")
	private String cid;
	@Column(name="ctype")
	private String ctype;
	@Column(name="cbatch")
	private String cbatch;
	@Column(name="csem")
	private String csem;
	@Column(name="cname")
	private String cname;
	@Column(name="alloted")
	private int alloted;
	@Column(name="cwhpw")
	private int cwhpw;
	public ClassesEntity() {}
	
	public ClassesEntity(String cid, String ctype, String cbatch,String cname, String csem, int alloted,int cwhpw) {
		super();
		this.cid = cid;
		this.ctype = ctype;
		this.cbatch = cbatch;
		this.cname = cname;
		this.csem = csem;
		this.alloted = alloted;
		this.cwhpw = cwhpw;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getCbatch() {
		return cbatch;
	}
	public void setCbatch(String cbatch) {
		this.cbatch = cbatch;
	}
	public String getCsem() {
		return csem;
	}
	public void setCsem(String csem) {
		this.csem = csem;
	}
	public int getAlloted() {
		return alloted;
	}
	public void setAlloted(int alloted) {
		this.alloted = alloted;
	}
	public int getCwhpw() {
		return cwhpw;
	}
	public void setCwhpw(int cwhpw) {
		this.cwhpw = cwhpw;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
	
}
