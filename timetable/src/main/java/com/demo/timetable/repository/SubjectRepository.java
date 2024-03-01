package com.demo.timetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.timetable.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity,String>{
	
	@Query(value="select cid from subjects u where u.sid=?1",nativeQuery=true)
	public String getSclass(String sid);

}
