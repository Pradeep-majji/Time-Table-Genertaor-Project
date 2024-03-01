package com.demo.timetable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.timetable.entity.TeacherTTEntity;
import com.demo.timetable.service.TeacherTTService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class TeacherTTController {


	@Autowired TeacherTTService TeacherTTService;
	
	@GetMapping(value="/teachertt/{tid}",produces="application/json")
	public ResponseEntity<TeacherTTEntity> getOneUser(@PathVariable String tid) {
		TeacherTTEntity e=TeacherTTService.getOneUser(tid);
		if(e!=null)
			return new ResponseEntity<TeacherTTEntity>(e,HttpStatus.OK);
		return new ResponseEntity<TeacherTTEntity>(e,HttpStatus.NOT_FOUND);		
	}
    @PostMapping(value="/addteachertt",consumes="application/json")
	public HttpStatus addUser(@RequestBody TeacherTTEntity ur)
	{
		if(TeacherTTService.insertUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
    @PutMapping(value="/resetteachertt",consumes="application/json")
	public HttpStatus modifyUser(@RequestBody TeacherTTEntity ur)
	{
		if(TeacherTTService.modifyUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_MODIFIED;
	}
}
