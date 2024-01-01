package com.backend.tt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tt.entity.SubjectEntity;
//import com.backend.tt.entity.TeacherEntity;
import com.backend.tt.service.SubjectService;
//import com.backend.tt.service.TeacherService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class SubjectController {


	@Autowired SubjectService SubjectService;
	
	@GetMapping(value="/subjectlist",produces="application/json")
	public  ResponseEntity<List<SubjectEntity>> getSubjects(){
		//System.out.println("in controller");
		return new ResponseEntity<List<SubjectEntity>>(SubjectService.getSubjects(),HttpStatus.OK);
	}
	@PostMapping(value="/addsubject",consumes="application/json")
	public HttpStatus addUser(@RequestBody SubjectEntity ur)
	{
		if(SubjectService.insertUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
}
