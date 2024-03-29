package com.demo.timetable.controller;

import java.util.List;

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

import com.demo.timetable.entity.ClassroomTTEntity;
import com.demo.timetable.entity.TeacherEntity;
import com.demo.timetable.service.ClassroomTTService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class ClassroomTTController{

	@Autowired
	ClassroomTTService ClassroomTTService;
	
	@GetMapping(value="/classroomttslist",produces="application/json")
	public  ResponseEntity<List<ClassroomTTEntity>> getList(){
		return new ResponseEntity<List<ClassroomTTEntity>>(ClassroomTTService.getList(),HttpStatus.OK);
	}
	
	@GetMapping(value="/classroomtt/{cid}",produces="application/json")
	public ResponseEntity<ClassroomTTEntity> getOneUser(@PathVariable String cid) {
		ClassroomTTEntity e=ClassroomTTService.getOneUser(cid);
		if(e!=null)
			return new ResponseEntity<ClassroomTTEntity>(e,HttpStatus.OK);
		return new ResponseEntity<ClassroomTTEntity>(e,HttpStatus.NOT_FOUND);		
	}
	@PostMapping(value="/addclassroomtt",consumes="application/json")
	public HttpStatus addUser(@RequestBody ClassroomTTEntity ur)
	{
		if(ClassroomTTService.insertUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	@PutMapping(value="/resetclassroomtt",consumes="application/json")
	public HttpStatus modifyUser(@RequestBody ClassroomTTEntity ur)
	{
		if(ClassroomTTService.modifyUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_MODIFIED;
	}
	
}
