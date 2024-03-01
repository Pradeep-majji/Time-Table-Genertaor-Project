package com.demo.timetable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="admin")
@Entity
public class AdminEntity {

	@Id
	@Column(name="adminid")
	private String adminid;
	@Column(name="password")
	private String password;
	@Column(name="ttg")
	private int ttg;
	public AdminEntity() {}
	public AdminEntity(String adminid, String password, int ttg) {
		super();
		this.adminid = adminid;
		this.password = password;
		this.ttg = ttg;
	}
	public String getAdminid() {
		return adminid;
	}
	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTtg() {
		return ttg;
	}
	public void setTtg(int ttg) {
		this.ttg = ttg;
	}
	
	
}
