package com.backend.tt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.tt.entity.SubjectEntity;
//import com.backend.tt.entity.TeacherEntity;
import com.backend.tt.repository.SubjectRepository;
@Service
public class SubjectService {

	@Autowired
	SubjectRepository SubjectRepository;
	
	@Transactional(readOnly=true)
	public List<SubjectEntity> getSubjects() {
		return SubjectRepository.findAll();	
	}
	@Transactional
	public boolean insertUser(SubjectEntity ur)
	{
		return SubjectRepository.save(ur) != null ;
	}
	
}
