package com.demo.timetable.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.timetable.service.AdminService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AdminController{

	@Autowired
	AdminService AdminService;
	
	@GetMapping(value="/verifyadmin/{email}/{password}",produces="application/json")
	public HttpStatus loginAuthenticate(@PathVariable String email,@PathVariable String password) {
		//System.out.print("in controller");
		if(AdminService.loginAuthenticate(email, password))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@GetMapping(value="/admin",produces="application/json")
	public HttpStatus generated() {
		//System.out.print("in controller");
		if(AdminService.generate())
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@PutMapping(value="/adminreset")
	public HttpStatus adminreset() {
		if(AdminService.modifyttg())
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	@PutMapping(value="/admingenerate")
	public HttpStatus admingenerate() {
		if(AdminService.generatettg())
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
}
