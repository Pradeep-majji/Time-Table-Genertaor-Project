package com.demo.timetable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.timetable.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity,String>{

	@Query(value="select count(*) from admin u where u.adminid=?1 and u.password=?2",nativeQuery=true)
	public int loginAuthenticate(String email,String Password);
	
	@Query(value="select ttg from admin u where u.adminid='admin'",nativeQuery=true)
	public int generate();


	@Modifying
	@Query(value="update admin u set u.ttg=0 where u.adminid='admin'",nativeQuery=true)
	  public int modifyttg();
}
