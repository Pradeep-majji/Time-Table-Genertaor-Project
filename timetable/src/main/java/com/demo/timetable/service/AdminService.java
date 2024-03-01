package com.demo.timetable.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.timetable.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository AdminRepository;
	
	@Transactional(readOnly=true)
	public boolean loginAuthenticate(String email,String password) {
		int count=AdminRepository.loginAuthenticate(email,password);
		return count>0;
	}
	
	@Transactional(readOnly=true)
	public boolean generate() {
		int count=AdminRepository.generate();
		return count>0;
	}
	
	@Transactional
	public boolean modifyttg()
	{
		int count=AdminRepository.modifyttg();
		return count>0;
	}
	@Transactional
	public boolean generatettg()
	{
		int count=AdminRepository.generatettg();
		return count>0;
	}

}
