package com.backend.tt.controller;

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

import com.backend.tt.entity.ClassesEntity;
import com.backend.tt.service.ClassesService;


@CrossOrigin(origins="http://localhost:3000")
@RestController
public class ClassesController {
	

	@Autowired
	ClassesService ClassesService;
	
	@GetMapping(value="/classroomslist",produces="application/json")
	public  ResponseEntity<List<ClassesEntity>> getAllotedClasses(){
		return new ResponseEntity<List<ClassesEntity>>(ClassesService.getAllotedClasses(),HttpStatus.OK);
	}
	@GetMapping(value="/classrooms",produces="application/json")
	public  ResponseEntity<List<ClassesEntity>> getClasses(){
		return new ResponseEntity<List<ClassesEntity>>(ClassesService.getClasses(),HttpStatus.OK);
	}
	@PutMapping(value="/updateclassroom/{cid}/{csem}/{ctype}/{cbatch}")
	public HttpStatus modifyUser(@PathVariable String cid,@PathVariable String csem,@PathVariable String ctype,@PathVariable String cbatch)
	{
		if(ClassesService.modifyUser(cid,csem,ctype,cbatch))
			return HttpStatus.OK;
		return HttpStatus.NOT_MODIFIED;
	}
	@PostMapping(value="/addclassroom",consumes="application/json")
	public HttpStatus addUser(@RequestBody ClassesEntity ur)
	{
		if(ClassesService.insertUser(ur))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@GetMapping(value="/verifyclassrooms/{cid}",produces="application/json")
	public HttpStatus loginAuthenticate(@PathVariable String email) {
		if(ClassesService.loginAuthenticate(email))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
}
