package com.demo.timetable.repository;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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
	
	@Query(value="select * from teachers u where u.tid=?1",nativeQuery=true)
	  public TeacherEntity getDataId(String email);
	
	@Query(value="select * from teachers u where u.verified=1",nativeQuery=true)
	  public List<TeacherEntity> getTeacherV();
	
	@Query(value="select tid from teachers u where u.verified=1",nativeQuery=true)
	public List<String> getIds();
	
	@Modifying
	@Query(value="update teachers u set u.verified=1,u.twhpw=?2 where u.tid=?1",nativeQuery=true)
	  public int teacherAccept(String email,int load);
    
    
    
    @Transactional
	@Modifying
	@Query(value="UPDATE teachers\r\n"
			+ "SET `twhpw` = \r\n"
			+ "    CASE \r\n"
			+ "        WHEN tdesignation = 'professor' THEN 12\r\n"
			+ "        WHEN tdesignation = 'associate'  THEN 14\r\n"
			+ "        WHEN tdesignation = 'assistant' THEN 20\r\n"
			+ "        ELSE 22 -- if designation is neither professor nor associate, keep the load_value as it is\r\n"
			+ "    END\r\n"
			+ ";",nativeQuery=true)
	  public void reset();
	
    
	@Transactional
	@Modifying
	@Query(value="insert into teachers(tid,tname,temail,tpassword,tdesignation,tspecialisation,twhpw,verified) values(?1,?2,?3,?4,?5,?6,0,1);",nativeQuery=true)
	  public void saveAdmin(String tid,String tname,String temail,String tpassword,String tdesignation,String tspecialisation);
	
	@Modifying
	@Query(value="update teachers u set u.twhpw=u.twhpw-?2 where u.tid=?1",nativeQuery=true)
	  public int putLoad(String tid,int load);
	

	@Query(value="select twhpw from teachers u where u.verified=1 and u.tid=?1",nativeQuery=true)
	  public int getLoad(String tid);
	
	default int putLoadOrDefault(String tid, int load) {
        try {
            return putLoad(tid,load);
        } catch (DataIntegrityViolationException ex) {
            // Handle the constraint violation exception
            // This might involve logging the error or returning a default value
          return 0	; // or any other default value
        }
    }
	
}


