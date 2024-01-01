package com.backend.tt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.tt.entity.ClassesEntity;

public interface ClassesRepository extends JpaRepository<ClassesEntity,String>{


	@Query(value="select * from classes u where u.alloted='1'",nativeQuery=true)
	public List<ClassesEntity> getAllotedClasses();

	@Query(value="select count(*) from classes u where u.cid=?1",nativeQuery=true)
	  public int loginAuthenticate(String email);
	
	@Modifying
	@Query(value="update classes u set u.alloted='1',u.csem=?2,u.ctype=?3,u.cbatch=?4 where u.cid=?1",nativeQuery=true)
	  public int classesUpdate(String cid,String csem,String ctype,String cbatch);
}
