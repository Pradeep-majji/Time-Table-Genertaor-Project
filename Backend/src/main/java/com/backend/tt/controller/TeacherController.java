package com.backend.tt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.backend.tt.entity.ClassesEntity;
import com.backend.tt.entity.TeacherEntity;
import com.backend.tt.service.TeacherService;



@CrossOrigin(origins="http://localhost:3000")
@RestController
public class TeacherController {
	
	@Autowired TeacherService TeacherService;
	
	@PostMapping(value="/addteacher",consumes="application/json")
	public HttpStatus addUser(@RequestBody TeacherEntity ur)
	{
		if(TeacherService.insertUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@GetMapping(value="/verifyteacher/{email}/{password}",produces="application/json")
	public HttpStatus loginAuthenticate(@PathVariable String email,@PathVariable String password) {
		if(TeacherService.loginAuthenticate(email, password))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	@DeleteMapping("/teacherdelete/{email}")
	public HttpStatus deleteUser(@PathVariable String email)
	{	System.out.println("in delete");
		if(TeacherService.deleteUser(email))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@GetMapping(value="/teacheruv",produces="application/json")
	public  ResponseEntity<List<TeacherEntity>> getAllotedClasses(){
		return new ResponseEntity<List<TeacherEntity>>(TeacherService.getTeacherUV(),HttpStatus.OK);
	}
	@GetMapping(value="/teacherid/{email}",produces="application/json")
	public  ResponseEntity<TeacherEntity> getId(@PathVariable String email){
		return new ResponseEntity<TeacherEntity>(TeacherService.getId(email),HttpStatus.OK);
	}
	@GetMapping(value="/teacherv",produces="application/json")
	public  ResponseEntity<List<TeacherEntity>> getTeacherV(){
		return new ResponseEntity<List<TeacherEntity>>(TeacherService.getTeacherV(),HttpStatus.OK);
	}
	
	@PutMapping(value="/teacheraccept/{email}")
	public HttpStatus teacherAccept(@PathVariable String email) {
		System.out.println("in accept");
		if(TeacherService.teacherAccept(email))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
}
