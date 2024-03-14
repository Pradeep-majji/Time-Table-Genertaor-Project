package com.demo.timetable.repository;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.timetable.entity.ClassesEntity;

public interface ClassesRepository extends JpaRepository<ClassesEntity,String>{


	@Query(value="select * from classes u where u.alloted='1'",nativeQuery=true)
	public List<ClassesEntity> getAllotedClasses();

	@Query(value="select count(*) from classes u where u.cid=?1",nativeQuery=true)
	  public int loginAuthenticate(String email);
	
	@Modifying
	@Query(value="update classes u set u.alloted='1',u.csem=?2,u.ctype=?3,u.cbatch=?4,u.cname=?5,u.cwhpw=22 where u.cid=?1",nativeQuery=true)
	  public int classesUpdate(String cid,String csem,String ctype,String cbatch,String cname);

	@Modifying
	@Query(value="update classes u set u.cwhpw=u.cwhpw-?2 where u.cid=?1",nativeQuery=true)
	  public int putLoad(String cid,int load);
	
	@Modifying
	@Query(value="update classes u set u.cwhpw=22",nativeQuery=true)
	  public void reset();
	
	@Query(value="select cid from classes u where u.alloted='1'",nativeQuery=true)
	public List<String> getIds();
	
	@Query(value="select cwhpw from classes u where u.alloted='1' and u.cid=?1",nativeQuery=true)
	public int getLoad(String tid);
	
	default int putLoadOrDefault(String tid, int load) {
        try {
            return putLoad(tid,load);
        } catch (DataIntegrityViolationException ex) {
            // Handle the constraint violation exception
            // This might involve logging the error or returning a default value
            return 0; // or any other default value
        }
    }
}
