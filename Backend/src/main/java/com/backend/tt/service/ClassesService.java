package com.backend.tt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.tt.entity.ClassesEntity;
import com.backend.tt.repository.ClassesRepository;

@Service
public class ClassesService {
	

	@Autowired
	ClassesRepository ClassesRepository;
	
	@Transactional(readOnly=true)
	public List<ClassesEntity> getAllotedClasses(){
		return ClassesRepository.getAllotedClasses();	
	}
	@Transactional(readOnly=true)
	public List<ClassesEntity> getClasses() {
		return ClassesRepository.findAll();	
	}
	@Transactional
	public boolean insertUser(ClassesEntity ur)
	{
		return ClassesRepository.save(ur)!=null;
	}
	@Transactional
	public boolean modifyUser(String cid,String csem,String ctype,String cbatch)
	{
		int count=ClassesRepository.classesUpdate(cid,csem,ctype,cbatch);
		return count>0;
	}
	@Transactional(readOnly=true)
	public boolean loginAuthenticate(String email) {
		int count=ClassesRepository.loginAuthenticate(email);
		return count>0;
	}
}
