package com.demo.timetable.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.backend.tt.entity.ClassesEntity;
import com.demo.timetable.entity.TeacherEntity;
import com.demo.timetable.entity.TeacherTTEntity;
import com.demo.timetable.repository.TeacherRepository;
import com.demo.timetable.repository.TeacherTTRepository;


@Service
public class TeacherService {

	@Autowired TeacherRepository TeacherRepository;
	@Autowired TeacherTTRepository TeacherTTRepository;

	
	@Transactional
	public boolean insertUser(TeacherEntity ur)
	{
		return TeacherRepository.save(ur)!=null;
	}
	
	@Transactional
	public boolean insertUserAdmin(TeacherEntity ur)
	{	String tid=ur.getTid();
		String tname=ur.getTname();
		String temail=ur.getTemail();
		String tpassword=ur.getTpassword();
		String tdesignation=ur.getTdesignation();
		String tspecialisation=ur.getTspecialisation();
		long count=TeacherRepository.count();
		TeacherRepository.saveAdmin(tid,tname,temail,tpassword,tdesignation,tspecialisation);
		return count<TeacherRepository.count();
	}
	
	@Transactional(readOnly=true)
	public boolean loginAuthenticate(String email,String password) {
		int count=TeacherRepository.loginAuthenticate(email,password);
		return count>0;
	}
	@Transactional
	public boolean deleteUser(String email)
	{
		long count=TeacherRepository.count();
		TeacherRepository.deleteById(email);
		return count>TeacherRepository.count();
	}
	@Transactional(readOnly=true)
	public List<TeacherEntity> getTeacherUV() {
		return TeacherRepository.getTeacherUV();	
	}
	@Transactional(readOnly=true)
	public List<TeacherEntity> getTeacherV() {
		return TeacherRepository.getTeacherV();	
	}
	@Transactional
	public boolean teacherAccept(String email,int load) {
		int count=TeacherRepository.teacherAccept(email,load);
		TeacherTTEntity ur=new TeacherTTEntity(email);
		if(TeacherTTRepository.save(ur)!=null)
		    return count>0;
		else return false;
	}
	@Transactional(readOnly=true)
	public TeacherEntity getId(String email) {
		return TeacherRepository.getId(email);	
	}
	@Transactional(readOnly=true)
	public TeacherEntity getById(String email) {
		return TeacherRepository.getDataId(email);	
	}
	@Transactional
	public boolean putLoadOrDefault(String tid,int load)
	{
		int count=TeacherRepository.putLoadOrDefault(tid,load);
		return count>0;
	}
	@Transactional(readOnly=true)
	public int getLoad(String tid) {
		return TeacherRepository.getLoad(tid);	
	}
	
}
