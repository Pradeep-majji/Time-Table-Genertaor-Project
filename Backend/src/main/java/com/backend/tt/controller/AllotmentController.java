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

import com.backend.tt.entity.AllotmentEntity;
import com.backend.tt.service.AllotmentService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AllotmentController {

	@Autowired AllotmentService AllotmentService;
	
	@PostMapping(value="/addallotment",consumes="application/json")
	public ResponseEntity<String> addUser(@RequestBody AllotmentEntity ur)
	{
		if (AllotmentService.insertUser(ur)) {
	        return new ResponseEntity<>("Allotment added successfully", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Failed to add allotment", HttpStatus.NOT_FOUND);
	    }
	}
	
	@PostMapping(value="/verifyallotment",consumes="application/json")
	public HttpStatus loginAuthenticate(@RequestBody AllotmentEntity.AllotmentId aid) {
		AllotmentEntity e=AllotmentService.loginAuthenticate(aid);
		if(e!=null)
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	
	@DeleteMapping("/allotmentdelete/{sid}/{cid}/{sem}/{batch}")
	public HttpStatus deleteUser(@PathVariable String sid,@PathVariable String cid,@PathVariable String sem,@PathVariable String batch)
	{   
		if(AllotmentService.deleteUser(sid,cid,sem,batch))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	@GetMapping(value="/allotmentuv",produces="application/json")
	public  ResponseEntity<List<AllotmentEntity>> getAllotments(){
		return new ResponseEntity<List<AllotmentEntity>>(AllotmentService.getAllotments(),HttpStatus.OK);
	}
	
	@PutMapping(value="/allotmentaccept/{sid}/{cid}/{sem}/{batch}")
	public HttpStatus allotmentAccept(@PathVariable String sid,@PathVariable String cid,@PathVariable String sem,@PathVariable String batch) {
		if(AllotmentService.allotmentAccept(sid,cid,sem,batch))
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	@GetMapping(value="/generatett",produces="application/json")
	public HttpStatus generateTT() {
		if(AllotmentService.generateTT())
			return HttpStatus.OK;
		return HttpStatus.NOT_FOUND;
	}
	
	
}
