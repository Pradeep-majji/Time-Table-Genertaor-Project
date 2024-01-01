package com.backend.tt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.backend.tt.entity.ClassesEntity;
import com.backend.tt.entity.TeacherEntity;
import com.backend.tt.repository.TeacherRepository;


@Service
public class TeacherService {

	@Autowired
	TeacherRepository TeacherRepository;
	
	@Transactional
	public boolean insertUser(TeacherEntity ur)
	{
		return TeacherRepository.save(ur)!=null;
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
	public boolean teacherAccept(String email) {
		int count=TeacherRepository.teacherAccept(email);
		return count>0;
	}
	@Transactional(readOnly=true)
	public TeacherEntity getId(String email) {
		return TeacherRepository.getId(email);	
	}
}
