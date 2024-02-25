package com.demo.timetable.controller;

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

import com.demo.timetable.entity.AllotmentEntity;
import com.demo.timetable.entity.AllotmentEntity.AllotmentId;
import com.demo.timetable.service.AllotmentService;
import com.demo.timetable.service.ClassesService;
import com.demo.timetable.service.TeacherService;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AllotmentController {

	@Autowired AllotmentService AllotmentService;
	@Autowired TeacherService TeacherService;
	@Autowired ClassesService ClassesService;
	
	@PostMapping(value="/addallotment",consumes="application/json")
	public ResponseEntity<String> addUser(@RequestBody AllotmentEntity ur)
	{
		if (AllotmentService.insertUser(ur)) {
			String type=ur.getType();
			AllotmentId id=ur.getId();
			String cid=id.getCid();
			String tid=ur.getTid();
			int load=2;
			if("lab".equals(type)) load=4;
			//increase load for teacher and class..
			if(ClassesService.modifyLoad(cid,load) && TeacherService.modifyLoad(tid, load))
			return new ResponseEntity<>("Allotment added successfully", HttpStatus.OK);
			else {
			boolean bool=AllotmentService.deleteUser(id.getSid(),id.getCid(),id.getSem(),id.getBatch());
		    return new ResponseEntity<>("Failed to add allotment", HttpStatus.NOT_FOUND);
			}
	    }
		else {
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
