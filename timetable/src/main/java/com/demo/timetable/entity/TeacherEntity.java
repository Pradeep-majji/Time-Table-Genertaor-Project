package com.demo.timetable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="teachers")
@Entity
public class TeacherEntity {

	@Id
	@Column(name="tid")
	private String tid;
	@Column(name="tname")
	private String tname;
	@Column(name="temail")
	private String temail;
	@Column(name="tpassword")
	private String tpassword;
	@Column(name="tdesignation")
	private String tdesignation;
	@Column(name="tspecialisation")
	private String tspecialisation;
	@Column(name="tsubjects")
	private String tsubjects;
	@Column(name="twhpw")
	private int twhpw;
	@Column(name="verified")
	private int verified;
	
	public TeacherEntity() {}
	public TeacherEntity(String tid, String tname, String temail, String tpassword, String tdesignation,
			String tspecialisation,String tsubjects, int twhpw, int verified) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.temail = temail;
		this.tpassword = tpassword;
		this.tdesignation = tdesignation;
		this.tspecialisation = tspecialisation;
		this.tsubjects=tsubjects;
		this.twhpw = twhpw;
		this.verified = verified;
	}
	
	public TeacherEntity(String tid, int twhpw){
		super();
		this.tid = tid;
		this.twhpw = twhpw;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTemail() {
		return temail;
	}
	public void setTemail(String temail) {
		this.temail = temail;
	}
	public String getTpassword() {
		return tpassword;
	}
	public void setTpassword(String tpassword) {
		this.tpassword = tpassword;
	}
	public String getTdesignation() {
		return tdesignation;
	}
	public void setTdesignation(String tdesignation) {
		this.tdesignation = tdesignation;
	}
	public String getTspecialisation() {
		return tspecialisation;
	}
	public void setTspecialisation(String tspecialisation) {
		this.tspecialisation = tspecialisation;
	}
	public int getTwhpw() {
		return twhpw;
	}
	public void setTwhpw(int twhpw) {
		this.twhpw = twhpw;
	}
	public int getVerified() {
		return verified;
	}
	public void setVerified(int verified) {
		this.verified = verified;
	}
	public String getTsubjects() {
		return tsubjects;
	}
	public void setTsubjects(String tsubjects) {
		this.tsubjects = tsubjects;
	}
	
}
