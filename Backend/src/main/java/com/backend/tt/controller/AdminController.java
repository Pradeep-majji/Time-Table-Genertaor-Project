package com.backend.tt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tt.service.AdminService;
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
	
}
