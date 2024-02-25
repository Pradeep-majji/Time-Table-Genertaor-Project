package com.demo.timetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.timetable.entity.AllotmentEntity;

public interface AllotmentRepository extends JpaRepository<AllotmentEntity,AllotmentEntity.AllotmentId>{

	@Query(value="select * from allotments u where u.verified=0",nativeQuery=true)
	  public List<AllotmentEntity> getAllotments();
	
	@Modifying
	@Query(value="update allotments u set u.verified=1 where u.sid=?1 and u.cid=?2 and u.sem=?3 and u.batch=?4 ",nativeQuery=true)
	  public int allotmentAccept(String sid,String cid,String sem,String batch);

	@Query(value="select * from allotments u where u.verified=1",nativeQuery=true)
	  public List<AllotmentEntity> getAllotmentsverified();

}
