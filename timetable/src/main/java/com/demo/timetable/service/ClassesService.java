package com.demo.timetable.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.timetable.entity.ClassesEntity;
import com.demo.timetable.entity.ClassroomTTEntity;
import com.demo.timetable.entity.TeacherTTEntity;
import com.demo.timetable.repository.ClassesRepository;
import com.demo.timetable.repository.ClassroomTTRepository;

@Service
public class ClassesService {
	

	@Autowired ClassesRepository ClassesRepository;
	@Autowired ClassroomTTRepository ClassroomTTRepository;
	
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
		if( ClassesRepository.save(ur)!= null) {
			ClassroomTTEntity t=new ClassroomTTEntity(ur.getCid());
			if (ClassroomTTRepository.save(t)!=null) return true;
			else return false;
		}
		else return false;
	}
	@Transactional
	public boolean modifyUser(String cid,String csem,String ctype,String cbatch)
	{
		int count=ClassesRepository.classesUpdate(cid,csem,ctype,cbatch);
		return count>0;
	}
	@Transactional
	public boolean putLoadOrDefault(String cid,int load)
	{
		int count=ClassesRepository.putLoadOrDefault(cid,load);
		    return count>0;	
	}
	@Transactional(readOnly=true)
	public int getLoad(String tid){
		return ClassesRepository.getLoad(tid);	
	}
	@Transactional(readOnly=true)
	public boolean loginAuthenticate(String email) {
		int count=ClassesRepository.loginAuthenticate(email);
		return count>0;
	}
}
