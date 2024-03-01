package com.demo.timetable.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.timetable.entity.ClassesEntity;
import com.demo.timetable.entity.ClassroomTTEntity;
import com.demo.timetable.repository.ClassroomTTRepository;


@Service
public class ClassroomTTService {

	@Autowired
	ClassroomTTRepository ClassroomTTRepository;
	
	@Transactional(readOnly=true)
	public ClassroomTTEntity getOneUser(String cid) {
		Optional<ClassroomTTEntity> u=ClassroomTTRepository.findById(cid);
		if(u.isPresent())
			return u.get();
		return null;
	}
	@Transactional(readOnly=true)
	public List<ClassroomTTEntity> getList() {
		return ClassroomTTRepository.findAll();	
	}
	@Transactional
	public boolean insertUser(ClassroomTTEntity ur)
	{
		return ClassroomTTRepository.save(ur)!=null;
	}
	@Transactional
	public boolean modifyUser(ClassroomTTEntity ur)
	{
		return ClassroomTTRepository.save(ur)!=null;
	}
	
}
