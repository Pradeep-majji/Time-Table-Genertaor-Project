package com.demo.timetable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.timetable.entity.TeacherEntity;

public interface TeacherRepository extends JpaRepository<TeacherEntity,String>{

	@Query(value="select count(*) from teachers u where u.temail=?1 and u.tpassword=?2",nativeQuery=true)
	  public int loginAuthenticate(String email,String Password);

	@Query(value="select * from teachers u where u.verified=0",nativeQuery=true)
	  public List<TeacherEntity> getTeacherUV();
	
	@Query(value="select * from teachers u where u.temail=?1",nativeQuery=true)
	  public TeacherEntity getId(String email);
	
	@Query(value="select * from teachers u where u.verified=1",nativeQuery=true)
	  public List<TeacherEntity> getTeacherV();
	
	@Modifying
	@Query(value="update teachers u set u.verified=1 where u.tid=?1",nativeQuery=true)
	  public int teacherAccept(String email);
	
	@Transactional
	@Modifying
	@Query(value="insert into teachers(tid,tname,temail,tpassword,tdesignation,tspecialisation,twhpw,verified) values(?1,?2,?3,?4,?5,?6,0,1);",nativeQuery=true)
	  public void saveAdmin(String tid,String tname,String temail,String tpassword,String tdesignation,String tspecialisation);
	
	@Modifying
	@Query(value="update teachers u set u.wphw=?2 where u.tid=?1",nativeQuery=true)
	  public int teacherUpdateLoad(String tid,int load);
	
}


