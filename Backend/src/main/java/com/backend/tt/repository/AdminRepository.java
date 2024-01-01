package com.backend.tt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.tt.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity,String>{

	@Query(value="select count(*) from admin u where u.adminid=?1 and u.password=?2",nativeQuery=true)
	public int loginAuthenticate(String email,String Password);
}
