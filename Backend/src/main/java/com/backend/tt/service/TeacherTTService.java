package com.backend.tt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.tt.entity.TeacherTTEntity;
import com.backend.tt.repository.TeacherTTRepository;

@Service
public class TeacherTTService {
	
	@Autowired
	TeacherTTRepository TeacherTTRepository;
	
	@Transactional(readOnly=true)
	public TeacherTTEntity getOneUser(String tid) {
		Optional<TeacherTTEntity> u=TeacherTTRepository.findById(tid);
		if(u.isPresent())
			return u.get();
		return null;
	}
	@Transactional
	public boolean insertUser(TeacherTTEntity ur)
	{
		return TeacherTTRepository.save(ur)!=null;
	}
	@Transactional
	public boolean modifyUser(TeacherTTEntity ur)
	{
		return TeacherTTRepository.save(ur)!=null;
	}
}
