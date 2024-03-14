package com.demo.timetable.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.timetable.entity.ClassroomTTEntity;

public interface ClassroomTTRepository extends JpaRepository<ClassroomTTEntity,String>{

	@Query(value="select * from classroomstts u where u.cid=?1",nativeQuery=true)
	  public ClassroomTTEntity findByCid(String cid);
}
