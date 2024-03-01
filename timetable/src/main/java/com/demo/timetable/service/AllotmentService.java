package com.demo.timetable.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.timetable.entity.AllotmentEntity;
import com.demo.timetable.entity.ClassroomTTEntity;
import com.demo.timetable.entity.SubjectEntity;
import com.demo.timetable.entity.TeacherTTEntity;
import com.demo.timetable.repository.AllotmentRepository;
import com.demo.timetable.repository.ClassesRepository;
import com.demo.timetable.repository.TeacherRepository;
import com.demo.timetable.repository.ClassroomTTRepository;
import com.demo.timetable.repository.TeacherTTRepository;
import com.demo.timetable.repository.SubjectRepository;


@Service
public class AllotmentService {
	
	@Autowired
	AllotmentRepository AllotmentRepository;
	@Autowired ClassroomTTRepository ClassroomTTRepository;
	@Autowired TeacherTTRepository TeacherTTRepository;
	@Autowired ClassesRepository ClassesRepository;
	@Autowired TeacherRepository TeacherRepository;
	@Autowired SubjectRepository SubjectRepository;
	
	@Transactional
    public boolean insertUser(AllotmentEntity ur) {
        try {
            AllotmentEntity savedEntity = AllotmentRepository.save(ur);
            System.out.print("in service try");
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("in service exception");
            return false; 
        }
    }
	
	@Transactional(readOnly=true)
	public AllotmentEntity loginAuthenticate(AllotmentEntity.AllotmentId aid) {
		//AllotmentEntity.AllotmentId aid=new AllotmentEntity.AllotmentId(sid,cid,sem,batch);
		Optional<AllotmentEntity> u=AllotmentRepository.findById(aid);
		if(u.isPresent())
			return u.get();
		return null;
	}

	
	
	@Transactional
	public boolean deleteUser(String sid,String cid,String sem,String batch)
	{   AllotmentEntity.AllotmentId aid = new AllotmentEntity.AllotmentId(sid,cid,sem,batch);
		long count=AllotmentRepository.count();
		AllotmentRepository.deleteById(aid);
		return count>AllotmentRepository.count();
	}
	@Transactional(readOnly=true)
	public List<AllotmentEntity> getAllotments() {
		return AllotmentRepository.getAllotments();
	}
	@Transactional
	public boolean allotmentAccept(String sid,String cid,String sem,String batch,String type,String tid) 
	{
		int count=AllotmentRepository.allotmentAccept(sid,cid,sem,batch);
		int load=2;
		if(type.equalsIgnoreCase("lab")) load=4;
		int tload=TeacherRepository.putLoadOrDefault(tid,load);
		int cload=ClassesRepository.putLoadOrDefault(cid, load);
		return count>0;
		
	}

	/////////////////////for resetting time table //////////////////////////////
	
	@Transactional
	public boolean resetTT() {
		List<String> classesList=ClassesRepository.getIds();
		List<String> teacherList=TeacherRepository.getIds();
		TeacherTTRepository.deleteAll();
		ClassroomTTRepository.deleteAll();
		TeacherRepository.reset();
		ClassesRepository.reset();
		List<TeacherTTEntity> teacherTTEntity=new ArrayList();
		List<ClassroomTTEntity> classroomTTEntity=new ArrayList();
		for(String id:teacherList){
			TeacherTTEntity teacher=new TeacherTTEntity(id);
			teacherTTEntity.add(teacher);
		}
		for(String id:classesList){
			ClassroomTTEntity teacher=new ClassroomTTEntity(id);
			classroomTTEntity.add(teacher);
		}
		TeacherTTRepository.saveAll(teacherTTEntity);
		ClassroomTTRepository.saveAll(classroomTTEntity);
		
		return true;
		
	}
	
	//////////////////////   for generation of time table  //////////////////////////////////////
 	@Transactional
	public boolean generateTT() {
		
		List<AllotmentEntity> allotmentList = AllotmentRepository.getAllotmentsverified();
		List<ClassroomTTEntity> classroomTimetable = ClassroomTTRepository.findAll();
		List<TeacherTTEntity> teacherTimetable = TeacherTTRepository.findAll();
		List<SubjectEntity> subjectsList = SubjectRepository.findAll();
		
		
		//allotments are to be sorted as per their designation and shuffled
		
		Map<String, Integer> designationPriorities = new HashMap<>();
        designationPriorities.put("professor", 1);
        designationPriorities.put("associate", 2);
        designationPriorities.put("assistant", 3);
        
////////////////////////////////////////    main code    /////////////////////////////////////////////////////////
        
            //  Minors/Honors  ,OE 
        assignMionorsHonorsClasses(classroomTimetable);
    	assignOEClasses(classroomTimetable);
  			//  Shuffling  ,  Sorting 
       Collections.shuffle(allotmentList);
       Collections.sort(allotmentList, Comparator.comparingInt(o -> designationPriorities.get(o.getTdesignation())));	

            /// LABS , Theory    
       	    List<AllotmentEntity> labAllotments = new ArrayList<>();
			List<AllotmentEntity> theoryAllotments = new ArrayList<>();

			for (AllotmentEntity allotment : allotmentList) {
    				if ("lab".equalsIgnoreCase(allotment.getType())) {
        					labAllotments.add(allotment);
    		} else if ("theory".equalsIgnoreCase(allotment.getType())) {
        					theoryAllotments.add(allotment);
    			}
			} 
        	
        	assignLabClasses(labAllotments, classroomTimetable, teacherTimetable,subjectsList);
        	assignTheoryClasses(theoryAllotments, classroomTimetable, teacherTimetable);

           //update in database //
        	ClassroomTTRepository.saveAll(classroomTimetable);
            TeacherTTRepository.saveAll(teacherTimetable);

        	assignTheoryClasses(theoryAllotments, classroomTimetable, teacherTimetable);
        	ClassroomTTRepository.saveAll(classroomTimetable);
            TeacherTTRepository.saveAll(teacherTimetable);
        	
           return true;
          }
	
	
	
///////////////////////////     assign honors minors classes ///////////////////////////////////////////
	 public static void assignMionorsHonorsClasses(List<ClassroomTTEntity> classroomTimetable) {
		 for(ClassroomTTEntity teacher: classroomTimetable) {
           	 teacher.setP16("Honors/Minors");
           	 teacher.setP20("Honors/Minors");
            }
	 }
	
	
//////////////////////////       assign open elective classes ///////////////////////////////////////////
	 public static void assignOEClasses(List<ClassroomTTEntity> classroomTimetable) {
	        for(ClassroomTTEntity teacher: classroomTimetable) {
               	 teacher.setP15("OE-1");
               	 teacher.setP19("OE-2");
	            }
	 }

//////////////////////////      assigning lab classes     //////////////////////////////////////////////
    public static void assignLabClasses(List<AllotmentEntity> labAllotments, List<ClassroomTTEntity> classroomTimetable, List<TeacherTTEntity> teacherTimetable,List<SubjectEntity> subjectsList) {
        for (AllotmentEntity allotment : labAllotments) {
            if ("lab".equalsIgnoreCase(allotment.getType())) {
            	// retrieve tid , cid and send to available functions
                String tid=allotment.getTid();
                String cid=allotment.getId().getCid();                
                String sid=allotment.getId().getSid();
                String firstAvailablePeriod = findAvailablePeriodForLab(allotment,teacherTimetable,tid, classroomTimetable,cid,subjectsList,sid,4);
                if (firstAvailablePeriod != null) {
                    assignPeriodLab(allotment, firstAvailablePeriod, teacherTimetable,tid, classroomTimetable,cid,subjectsList,sid);
                }
            }
        }
    }
//////////////////////////          availability of lab classes first ones    ///////////////////////////////////////////////////
    
    
    public static String findAvailablePeriodForLab(AllotmentEntity allotment,List<TeacherTTEntity> teacherTimetable,String tid, List<ClassroomTTEntity> classroomTimetable,String cid,List<SubjectEntity> subjectsList,String sid, int maxPerDay) {
    List<String> commonAvailablePeriods = new ArrayList<>();
    List<String> allPeriods=new ArrayList<>();
    for (int i = 1; i <= 22; i+=2) {
        allPeriods.add("p" + i);
    }
    for (String period : allPeriods) {
        if (isAvailableForLabTeacher(allotment,period,teacherTimetable,tid,maxPerDay) && isAvailableForLabStudents(allotment,period, classroomTimetable,cid, maxPerDay) && isAvailableForLabClass(allotment,period, classroomTimetable,sid,subjectsList, maxPerDay)) {
            commonAvailablePeriods.add(period);
        }
    }

    return commonAvailablePeriods.isEmpty() ? null : commonAvailablePeriods.get(0);
}
        ///////////////////      is available for teacher lab      ////////////////////////////////////
public static boolean isAvailableForLabTeacher(AllotmentEntity allotment,String period, List<TeacherTTEntity> teacherTimetable,String tid, int maxPerDay) {
	for (TeacherTTEntity teacher : teacherTimetable) {
        if (teacher.getTid().equalsIgnoreCase(tid)) {
            // Check if the specified period is free for the teacher
            switch (period) {
                case "p1":
                    if ((teacher.getP1() == null || teacher.getP1().isEmpty()) && (teacher.getP2() == null || teacher.getP2().isEmpty() )&&
                    	!teacher.getP3().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getCid())) 
                    return true;
                    break;
                case "p3":
                	if ((teacher.getP3() == null || teacher.getP3().isEmpty()) && (teacher.getP4() == null || teacher.getP4().isEmpty() )&&
                	!teacher.getP1().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP2().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;
                break;
                case "p5":
                	if ((teacher.getP5() == null || teacher.getP5().isEmpty()) && (teacher.getP6() == null || teacher.getP6().isEmpty())
                	&& !teacher.getP7().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;
                break;
                case "p7":
                	if ((teacher.getP7() == null || teacher.getP7().isEmpty()) &&( teacher.getP8() == null || teacher.getP8().isEmpty()) 
                	&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP6().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p9":
                	if ((teacher.getP9() == null || teacher.getP9().isEmpty()) && (teacher.getP10() == null || teacher.getP10().isEmpty()) &&
                	!teacher.getP11().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p11":
                	if ((teacher.getP11() == null || teacher.getP11().isEmpty()) &&( teacher.getP12() == null || teacher.getP12().isEmpty() )&&
                	!teacher.getP9().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP10().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p13":
                	if ((teacher.getP13() == null || teacher.getP13().isEmpty()) && (teacher.getP14() == null || teacher.getP14().isEmpty() )&&
                	!teacher.getP15().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p15":
                	if ((teacher.getP15() == null || teacher.getP15().isEmpty()) && (teacher.getP16() == null || teacher.getP16().isEmpty() )&&
                	!teacher.getP13().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP14().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p17":
                	if ((teacher.getP17() == null || teacher.getP17().isEmpty()) && (teacher.getP18() == null || teacher.getP18().isEmpty()) &&
                	!teacher.getP19().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p19":
                	if ((teacher.getP19() == null || teacher.getP19().isEmpty()) && (teacher.getP20() == null || teacher.getP20().isEmpty() )&&
                	!teacher.getP17().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP18().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;break;
                case "p21":
                	if ((teacher.getP21() == null || teacher.getP21().isEmpty()) && (teacher.getP22() == null || teacher.getP22().isEmpty())) 
                return true;break;
                default:
                    return false; // Invalid period
            }
        }
    }
    return false;
}
	//////////////////////////       is available for students and  labs            ///////////////////////////////////

public static boolean isAvailableForLabStudents(AllotmentEntity allotment,String period, List<ClassroomTTEntity> teacherTimetable,String cid, int maxPerDay){
	for (ClassroomTTEntity teacher : teacherTimetable) {
        if (teacher.getCid().equals(cid)) {
            // Check if the specified period is free for the teacher
        	switch (period) {
            case "p1":
                if ((teacher.getP1() == null || teacher.getP1().isEmpty()) &&( teacher.getP2() == null || teacher.getP2().isEmpty() )&&
                	!teacher.getP3().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getSid())) 
                return true;
                break;
            case "p3":
            	if ((teacher.getP3() == null || teacher.getP3().isEmpty()) && (teacher.getP4() == null || teacher.getP4().isEmpty() )&&
            	!teacher.getP1().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP2().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;
            break;
            case "p5":
            	if ((teacher.getP5() == null || teacher.getP5().isEmpty()) && (teacher.getP6() == null || teacher.getP6().isEmpty())
            	&& !teacher.getP7().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;
            break;
            case "p7":
            	if ((teacher.getP7() == null || teacher.getP7().isEmpty()) &&( teacher.getP8() == null || teacher.getP8().isEmpty()) 
            	&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP6().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p9":
            	if ((teacher.getP9() == null || teacher.getP9().isEmpty()) && (teacher.getP10() == null || teacher.getP10().isEmpty()) &&
            	!teacher.getP11().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p11":
            	if ((teacher.getP11() == null || teacher.getP11().isEmpty()) &&( teacher.getP12() == null || teacher.getP12().isEmpty()) &&
            	!teacher.getP9().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP10().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p13":
            	if ((teacher.getP13() == null || teacher.getP13().isEmpty()) && (teacher.getP14() == null || teacher.getP14().isEmpty() )&&
            	!teacher.getP15().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p15":
            	if ((teacher.getP15() == null || teacher.getP15().isEmpty()) && (teacher.getP16() == null || teacher.getP16().isEmpty() )&&
            	!teacher.getP13().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP14().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p17":
            	if ((teacher.getP17() == null || teacher.getP17().isEmpty()) && (teacher.getP18() == null || teacher.getP18().isEmpty()) &&
            	!teacher.getP19().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p19":
            	if ((teacher.getP19() == null || teacher.getP19().isEmpty()) && (teacher.getP20() == null || teacher.getP20().isEmpty()) &&
            	!teacher.getP17().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP18().equalsIgnoreCase(allotment.getId().getSid())) 
            return true;break;
            case "p21":
            	if ((teacher.getP21() == null || teacher.getP1().isEmpty()) && (teacher.getP22() == null || teacher.getP2().isEmpty())) 
            return true;break;
            default:
                return false;
               }
        	}
		}
        return false;

}


public static boolean isAvailableForLabClass(AllotmentEntity allotment,String period, List<ClassroomTTEntity> teacherTimetable,String sid,List<SubjectEntity> subjectsList, int maxPerDay){
	String cid="";
	for(SubjectEntity subject:subjectsList) {
		if(subject.getSid().equalsIgnoreCase(sid)) cid=subject.getSclass();
	}
	for (ClassroomTTEntity teacher : teacherTimetable) {
        if (teacher.getCid().equals(cid) && !teacher.getCid().equals("")) {
            // Check if the specified period is free for the teacher
        	switch (period) {
            case "p1":
                if ((teacher.getP1() == null || teacher.getP1().isEmpty()) && (teacher.getP2() == null || teacher.getP2().isEmpty() )&&
                	!teacher.getP3().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getCid())) 
                return true;
                break;
            case "p3":
            	if ((teacher.getP3() == null || teacher.getP3().isEmpty()) && (teacher.getP4() == null || teacher.getP4().isEmpty() )&&
            	!teacher.getP1().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP2().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;
            break;
            case "p5":
            	if ((teacher.getP5() == null || teacher.getP5().isEmpty()) && (teacher.getP6() == null || teacher.getP6().isEmpty())
            	&& !teacher.getP7().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;
            break;
            case "p7":
            	if ((teacher.getP7() == null || teacher.getP7().isEmpty()) &&( teacher.getP8() == null || teacher.getP8().isEmpty()) 
            	&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP6().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p9":
            	if ((teacher.getP9() == null || teacher.getP9().isEmpty()) && (teacher.getP10() == null || teacher.getP10().isEmpty()) &&
            	!teacher.getP11().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p11":
            	if ((teacher.getP11() == null || teacher.getP11().isEmpty()) &&( teacher.getP12() == null || teacher.getP12().isEmpty() )&&
            	!teacher.getP9().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP10().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p13":
            	if ((teacher.getP13() == null || teacher.getP13().isEmpty()) && (teacher.getP14() == null || teacher.getP14().isEmpty() )&&
            	!teacher.getP15().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p15":
            	if ((teacher.getP15() == null || teacher.getP15().isEmpty()) && (teacher.getP16() == null || teacher.getP16().isEmpty() )&&
            	!teacher.getP13().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP14().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p17":
            	if ((teacher.getP17() == null || teacher.getP17().isEmpty()) && (teacher.getP18() == null || teacher.getP18().isEmpty()) &&
            	!teacher.getP19().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p19":
            	if ((teacher.getP19() == null || teacher.getP19().isEmpty()) && (teacher.getP20() == null || teacher.getP20().isEmpty() )&&
            	!teacher.getP17().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP18().equalsIgnoreCase(allotment.getId().getCid())) 
            return true;break;
            case "p21":
            	if ((teacher.getP21() == null || teacher.getP21().isEmpty()) && (teacher.getP22() == null || teacher.getP22().isEmpty())) 
            return true;break;
            default:
                return false;
                }
        	}
		}
        return false;

}







//////////////////////////////////////////assigning the theory classes       /////////////////////////////////////////////


public static void assignTheoryClasses(List<AllotmentEntity> theoryAllotments, List<ClassroomTTEntity> classroomTimetable, List<TeacherTTEntity> teacherTimetable) {
for (AllotmentEntity allotment : theoryAllotments) {
if ("theory".equalsIgnoreCase(allotment.getType())) {
String tid=allotment.getTid();
String cid=allotment.getId().getCid();
String firstAvailablePeriod = findAvailablePeriodForTheory(allotment,teacherTimetable,tid, classroomTimetable,cid, 1);
if (firstAvailablePeriod != null) {
assignPeriod(allotment, firstAvailablePeriod, teacherTimetable,tid, classroomTimetable,cid);
}
}
}
}
////////////////////////availability of theory classes     ////////////////////////////////////////////

public static String findAvailablePeriodForTheory(AllotmentEntity allotment,List<TeacherTTEntity> teacherTimetable,String tid ,List<ClassroomTTEntity> classroomTimetable,String cid, int maxPerDay) {
List<String> commonAvailablePeriods = new ArrayList<>();
List<String> allPeriods=new ArrayList<>();
for (int i = 1; i <= 22; i++) {
allPeriods.add("p" + i);
}
for (String period : allPeriods) {
if (isAvailableForTheoryTeacher(allotment,period, teacherTimetable,tid, maxPerDay) && isAvailableForTheoryClass(allotment,period, classroomTimetable,cid, maxPerDay)) {
commonAvailablePeriods.add(period);
}
}

return commonAvailablePeriods.isEmpty() ? null : commonAvailablePeriods.get(0);
}




/////////////////////////        is available for class theory               /////////////////////////

public static boolean isAvailableForTheoryClass(AllotmentEntity allotment,String period, List<ClassroomTTEntity> teacherTimetable,String cid, int maxPerDay) {
for (ClassroomTTEntity teacher : teacherTimetable) {
if (teacher.getCid().equalsIgnoreCase(cid)) {
// Check if the specified period is free for the teacher
	switch (period) {
	case "p1":
	if ((teacher.getP1() == null || teacher.getP1().isEmpty()) && !teacher.getP2().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP3().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p2":
		if ((teacher.getP2() == null || teacher.getP2().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP3().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p3":
		if ((teacher.getP3() == null || teacher.getP3().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP2().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p4":
		if ((teacher.getP4() == null || teacher.getP4().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP2().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP3().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p5":
		if ((teacher.getP5() == null || teacher.getP5().isEmpty())&& !teacher.getP6().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP7().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p6":
		if ((teacher.getP6() == null || teacher.getP6().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP7().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p7":
		if ((teacher.getP7() == null || teacher.getP7().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP6().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p8":
		if ((teacher.getP8() == null || teacher.getP8().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP6().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP7().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p9":
		if ((teacher.getP9() == null || teacher.getP9().isEmpty())&& !teacher.getP10().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP11().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p10":
		if ((teacher.getP10() == null || teacher.getP10().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP11().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p11":
		if ((teacher.getP11() == null || teacher.getP11().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP10().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p12":
		if ((teacher.getP12() == null || teacher.getP12().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP10().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP11().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p13":
		if ((teacher.getP13() == null || teacher.getP13().isEmpty())&& !teacher.getP14().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP15().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p14":
		if ((teacher.getP14() == null || teacher.getP1().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP15().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p15":
		if ((teacher.getP15() == null || teacher.getP15().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP14().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p16":
		if ((teacher.getP16() == null || teacher.getP16().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP14().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP15().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p17":
		if ((teacher.getP17() == null || teacher.getP17().isEmpty())&& !teacher.getP18().equalsIgnoreCase(allotment.getId().getCid()) &&
		!teacher.getP19().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getCid())) 
	return true;break;
	case "p18":
		if ((teacher.getP18() == null || teacher.getP18().isEmpty())&& !teacher.getP19().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP21().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP22().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p19":
		if ((teacher.getP19() == null || teacher.getP19().isEmpty())&& !teacher.getP17().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP18().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p20":
		if ((teacher.getP20() == null || teacher.getP20().isEmpty())&& !teacher.getP17().equalsIgnoreCase(allotment.getId().getSid()) &&
		!teacher.getP18().equalsIgnoreCase(allotment.getId().getSid()) && !teacher.getP19().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p21":
		if ((teacher.getP21() == null || teacher.getP21().isEmpty())&& !teacher.getP22().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	case "p22":
		if ((teacher.getP22() == null || teacher.getP22().isEmpty())&& !teacher.getP21().equalsIgnoreCase(allotment.getId().getSid())) 
	return true;break;
	default:
	return false; // Invalid period
	}
	}
	}
	return false;
	}


///// is available for teacher theory /////////
public static boolean isAvailableForTheoryTeacher(AllotmentEntity allotment,String period, List<TeacherTTEntity> teacherTimetable,String tid, int maxPerDay) {
for (TeacherTTEntity teacher : teacherTimetable) {
if (teacher.getTid().equals(tid)) {
//Check if the specified period is free for the teacher
switch (period) {
case "p1":
if ((teacher.getP1() == null || teacher.getP1().isEmpty())&& !teacher.getP2().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP3().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p2":
if ((teacher.getP2() == null || teacher.getP2().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP3().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p3":
if ((teacher.getP3() == null || teacher.getP3().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP2().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP4().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p4":
if ((teacher.getP4() == null || teacher.getP4().isEmpty())&& !teacher.getP1().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP2().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP3().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p5":
if ((teacher.getP5() == null || teacher.getP5().isEmpty())&& !teacher.getP6().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP7().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p6":
if ((teacher.getP6() == null || teacher.getP6().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP7().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p7":
if ((teacher.getP7() == null || teacher.getP7().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP6().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP8().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p8":
if ((teacher.getP8() == null || teacher.getP8().isEmpty())&& !teacher.getP5().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP6().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP7().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p9":
if ((teacher.getP9() == null || teacher.getP9().isEmpty())&& !teacher.getP10().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP11().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p10":
if ((teacher.getP10() == null || teacher.getP10().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP11().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p11":
if ((teacher.getP11() == null || teacher.getP11().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP10().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP12().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p12":
if ((teacher.getP12() == null || teacher.getP12().isEmpty())&& !teacher.getP9().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP10().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP11().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p13":
if ((teacher.getP13() == null || teacher.getP13().isEmpty())&& !teacher.getP14().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP15().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p14":
if ((teacher.getP14() == null || teacher.getP1().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP15().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p15":
if ((teacher.getP15() == null || teacher.getP15().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP14().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP16().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p16":
if ((teacher.getP16() == null || teacher.getP16().isEmpty())&& !teacher.getP13().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP14().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP15().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p17":
if ((teacher.getP17() == null || teacher.getP17().isEmpty())&& !teacher.getP18().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP19().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP20().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p18":
if ((teacher.getP18() == null || teacher.getP18().isEmpty())&& !teacher.getP19().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP21().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP22().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p19":
if ((teacher.getP19() == null || teacher.getP19().isEmpty())&& !teacher.getP17().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP18().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP20().equals(allotment.getId().getCid())) 
return true;break;
case "p20":
if ((teacher.getP20() == null || teacher.getP20().isEmpty())&& !teacher.getP17().equalsIgnoreCase(allotment.getId().getCid()) &&
!teacher.getP18().equalsIgnoreCase(allotment.getId().getCid()) && !teacher.getP19().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p21":
if ((teacher.getP21() == null || teacher.getP21().isEmpty())&& !teacher.getP22().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
case "p22":
if ((teacher.getP22() == null || teacher.getP22().isEmpty()) && !teacher.getP21().equalsIgnoreCase(allotment.getId().getCid())) 
return true;break;
default:
return false; // Invalid period
}
}
}
return false;
}

/////////////////////////assign periods lab and store in the local list/////////////////////////////////////////////////

public static void assignPeriodLab(AllotmentEntity allotment, String period, List<TeacherTTEntity> teacherTimetable,String tid, List<ClassroomTTEntity> classroomTimetable,String cid,List<SubjectEntity> subjectsList,String sid) {
    // Update the teacher timetable lab
    updateTeacherTimetableLab(allotment,tid, period, teacherTimetable);

    // Update the class-student timetable
    updateClassroomStudentTimetableLab(allotment,cid, period, classroomTimetable);
    
    String cidBySid="";
	for(SubjectEntity subject:subjectsList) {
		if(subject.getSid().equalsIgnoreCase(sid)) cidBySid=subject.getSclass();
	}
	// Update the class-lab timetable
    updateClassroomTimetableLab(allotment,cidBySid, period, classroomTimetable);
    
}

   
    public static void updateTeacherTimetableLab(AllotmentEntity allotment,String id, String period, List<TeacherTTEntity> timetable) {
        for(TeacherTTEntity teacher: timetable) {
         if(teacher.getTid().equals(id)) {
        	 switch (period) {
             case "p1":
            	 teacher.setP1(allotment.getId().getCid());
            	 teacher.setP2(allotment.getId().getCid());
                 break;
             case "p3":
            	 teacher.setP3(allotment.getId().getCid());
            	 teacher.setP4(allotment.getId().getCid());
                 break;
             case "p5":
            	 teacher.setP5(allotment.getId().getCid());
            	 teacher.setP6(allotment.getId().getCid());
                 break;
             case "p7":
            	 teacher.setP7(allotment.getId().getCid());
            	 teacher.setP8(allotment.getId().getCid());
                 break;
             case "p9":
            	 teacher.setP9(allotment.getId().getCid());
            	 teacher.setP10(allotment.getId().getCid());
                 break;
             case "p11":
            	 teacher.setP11(allotment.getId().getCid());
            	 teacher.setP12(allotment.getId().getCid());
                 break;
             case "p13":
            	 teacher.setP13(allotment.getId().getCid());
            	 teacher.setP14(allotment.getId().getCid());
                 break;
             case "p15":
            	 teacher.setP15(allotment.getId().getCid());
            	 teacher.setP16(allotment.getId().getCid());
                 break;
             case "p17":
            	 teacher.setP17(allotment.getId().getCid());
            	 teacher.setP18(allotment.getId().getCid());
                 break;
             case "p19":
            	 teacher.setP19(allotment.getId().getCid());
            	 teacher.setP20(allotment.getId().getCid());
                 break;
             case "p21":
            	 teacher.setP21(allotment.getId().getCid());
            	 teacher.setP22(allotment.getId().getCid());
                 break;
             case "p22":
                 break;
             default:
                 break; // Invalid period
         }
         }
        }
    }

    public static void updateClassroomStudentTimetableLab(AllotmentEntity allotment,String id, String period, List<ClassroomTTEntity> timetable) {
        for(ClassroomTTEntity teacher: timetable) {
            if(teacher.getCid().equals(id)) {
            	switch (period) {
                case "p1":
               	 teacher.setP1(allotment.getId().getSid());
               	 teacher.setP2(allotment.getId().getSid());
                    break;
                case "p3":
               	 teacher.setP3(allotment.getId().getSid());
               	 teacher.setP4(allotment.getId().getSid());
                    break;
                case "p5":
               	 teacher.setP5(allotment.getId().getSid());
               	 teacher.setP6(allotment.getId().getSid());
                    break;
                case "p7":
               	 teacher.setP7(allotment.getId().getSid());
               	 teacher.setP8(allotment.getId().getSid());
                    break;
                case "p9":
               	 teacher.setP9(allotment.getId().getSid());
               	 teacher.setP10(allotment.getId().getSid());
                    break;
                case "p11":
               	 teacher.setP11(allotment.getId().getSid());
               	 teacher.setP12(allotment.getId().getSid());
                    break;
                case "p13":
               	 teacher.setP13(allotment.getId().getSid());
               	 teacher.setP14(allotment.getId().getSid());
                    break;
                case "p15":
               	 teacher.setP15(allotment.getId().getSid());
               	 teacher.setP16(allotment.getId().getSid());
                    break;
                case "p17":
               	 teacher.setP17(allotment.getId().getSid());
               	 teacher.setP18(allotment.getId().getSid());
                    break;
                case "p19":
               	 teacher.setP19(allotment.getId().getSid());
               	 teacher.setP20(allotment.getId().getSid());
                    break;
                case "p21":
               	 teacher.setP21(allotment.getId().getSid());
               	 teacher.setP22(allotment.getId().getSid());
                    break;
                case "p22":
                    break;
                default:
                    break; // Invalid period
            }
            }
           }
    }
    
    
    public static void updateClassroomTimetableLab(AllotmentEntity allotment,String id, String period, List<ClassroomTTEntity> timetable) {
        for(ClassroomTTEntity teacher: timetable) {
            if(teacher.getCid().equals(id)) {
            	switch (period) {
            	case "p1":
               	 teacher.setP1(allotment.getId().getCid());
               	 teacher.setP2(allotment.getId().getCid());
                    break;
                case "p3":
               	 teacher.setP3(allotment.getId().getCid());
               	 teacher.setP4(allotment.getId().getCid());
                    break;
                case "p5":
               	 teacher.setP5(allotment.getId().getCid());
               	 teacher.setP6(allotment.getId().getCid());
                    break;
                case "p7":
               	 teacher.setP7(allotment.getId().getCid());
               	 teacher.setP8(allotment.getId().getCid());
                    break;
                case "p9":
               	 teacher.setP9(allotment.getId().getCid());
               	 teacher.setP10(allotment.getId().getCid());
                    break;
                case "p11":
               	 teacher.setP11(allotment.getId().getCid());
               	 teacher.setP12(allotment.getId().getCid());
                    break;
                case "p13":
               	 teacher.setP13(allotment.getId().getCid());
               	 teacher.setP14(allotment.getId().getCid());
                    break;
                case "p15":
               	 teacher.setP15(allotment.getId().getCid());
               	 teacher.setP16(allotment.getId().getCid());
                    break;
                case "p17":
               	 teacher.setP17(allotment.getId().getCid());
               	 teacher.setP18(allotment.getId().getCid());
                    break;
                case "p19":
               	 teacher.setP19(allotment.getId().getCid());
               	 teacher.setP20(allotment.getId().getCid());
                    break;
                case "p21":
               	 teacher.setP21(allotment.getId().getCid());
               	 teacher.setP22(allotment.getId().getCid());
                    break;
                case "p22":
                    break;
                default:
                    break; // Invalid period
            }
            }
           }
    }
    
    
    ////////////////           assign periods theory and store in the local list      /////////////////////////////////

    public static void assignPeriod(AllotmentEntity allotment, String period, List<TeacherTTEntity> teacherTimetable,String tid, List<ClassroomTTEntity> classroomTimetable,String cid) {
        // Update the teacher timetable theory
        updateTeacherTimetable(allotment,tid, period, teacherTimetable);

        // Update the classroom timetable theory
        updateClassroomTimetable(allotment,cid, period, classroomTimetable);
    }
    
    public static void updateTeacherTimetable(AllotmentEntity allotment,String id, String period, List<TeacherTTEntity> timetable) {
        for(TeacherTTEntity teacher: timetable) {
         if(teacher.getTid().equals(id)) {
        	 switch (period) {
             case "p1":
            	 teacher.setP1(allotment.getId().getCid());
                 break;
             case "p2":
            	 teacher.setP2(allotment.getId().getCid());
                 break;
             case "p3":
            	 teacher.setP3(allotment.getId().getCid());
                 break;
             case "p4":
            	 teacher.setP4(allotment.getId().getCid());
                 break;
             case "p5":
            	 teacher.setP5(allotment.getId().getCid());
                 break;
             case "p6":
            	 teacher.setP6(allotment.getId().getCid());
                 break;
             case "p7":
            	 teacher.setP7(allotment.getId().getCid());
                 break;
             case "p8":
            	 teacher.setP8(allotment.getId().getCid());
                 break;
             case "p9":
            	 teacher.setP9(allotment.getId().getCid());
                 break;
             case "p10":
            	 teacher.setP10(allotment.getId().getCid());
                 break;
             case "p11":
            	 teacher.setP11(allotment.getId().getCid());
                 break;
             case "p12":
            	 teacher.setP12(allotment.getId().getCid());
                 break;
             case "p13":
            	 teacher.setP13(allotment.getId().getCid());
                 break;
             case "p14":
            	 teacher.setP14(allotment.getId().getCid());
                 break;
             case "p15":
            	 teacher.setP15(allotment.getId().getCid());
                 break;
             case "p16":
            	 teacher.setP16(allotment.getId().getCid());
                 break;
             case "p17":
            	 teacher.setP17(allotment.getId().getCid());
                 break;
             case "p18":
            	 teacher.setP18(allotment.getId().getCid());
                 break;
             case "p19":
            	 teacher.setP19(allotment.getId().getCid());
                 break;
             case "p20":
            	 teacher.setP20(allotment.getId().getCid());
                 break;
             case "p21":
            	 teacher.setP21(allotment.getId().getCid());
                 break;
             case "p22":
            	 teacher.setP22(allotment.getId().getCid());
                 break;
             default:
                 break; // Invalid period
         }
         }
        }
    }

    public static void updateClassroomTimetable(AllotmentEntity allotment,String id, String period, List<ClassroomTTEntity> timetable) {
        for(ClassroomTTEntity teacher: timetable) {
            if(teacher.getCid().equals(id)) {
           	 switch (period) {
                case "p1":
               	 teacher.setP1(allotment.getId().getSid());
                    break;
                case "p2":
               	 teacher.setP2(allotment.getId().getSid());
                    break;
                case "p3":
               	 teacher.setP3(allotment.getId().getSid());
                    break;
                case "p4":
               	 teacher.setP4(allotment.getId().getSid());
                    break;
                case "p5":
               	 teacher.setP5(allotment.getId().getSid());
                    break;
                case "p6":
               	 teacher.setP6(allotment.getId().getSid());
                    break;
                case "p7":
               	 teacher.setP7(allotment.getId().getSid());
                    break;
                case "p8":
               	 teacher.setP8(allotment.getId().getSid());
                    break;
                case "p9":
               	 teacher.setP9(allotment.getId().getSid());
                    break;
                case "p10":
               	 teacher.setP10(allotment.getId().getSid());
                    break;
                case "p11":
               	 teacher.setP11(allotment.getId().getSid());
                    break;
                case "p12":
               	 teacher.setP12(allotment.getId().getSid());
                    break;
                case "p13":
               	 teacher.setP13(allotment.getId().getSid());
                    break;
                case "p14":
               	 teacher.setP14(allotment.getId().getSid());
                    break;
                case "p15":
               	 teacher.setP15(allotment.getId().getSid());
                    break;
                case "p16":
               	 teacher.setP16(allotment.getId().getSid());
                    break;
                case "p17":
               	 teacher.setP17(allotment.getId().getSid());
                    break;
                case "p18":
               	 teacher.setP18(allotment.getId().getSid());
                    break;
                case "p19":
               	 teacher.setP19(allotment.getId().getSid());
                    break;
                case "p20":
               	 teacher.setP20(allotment.getId().getSid());
                    break;
                case "p21":
               	 teacher.setP21(allotment.getId().getSid());
                    break;
                case "p22":
               	 teacher.setP22(allotment.getId().getSid());
                    break;
                default:
                    break; // Invalid period
            }
            }
           }
    }


}
