package com.backend.tt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.tt.entity.AllotmentEntity;
import com.backend.tt.entity.ClassroomTTEntity;
import com.backend.tt.entity.TeacherTTEntity;
import com.backend.tt.repository.AllotmentRepository;
import com.backend.tt.repository.ClassroomTTRepository;
import com.backend.tt.repository.TeacherTTRepository;

@Service
public class AllotmentService {
	
	@Autowired
	AllotmentRepository AllotmentRepository;
	ClassroomTTRepository ClassroomTTRepository;
	TeacherTTRepository TeacherTTRepository;
	
	@Transactional
    public boolean insertUser(AllotmentEntity ur) {
        try {
            AllotmentEntity savedEntity = AllotmentRepository.save(ur);
            return savedEntity != null; 
        } catch (Exception e) {
            e.printStackTrace();
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
	public boolean allotmentAccept(String sid,String cid,String sem,String batch) 
	{
		int count=AllotmentRepository.allotmentAccept(sid,cid,sem,batch);
		return count>0;
	}

	
	
	
	//for generation of time table 
	@Transactional
	public boolean generateTT() {
		/*
		List<AllotmentEntity> allotmentList = AllotmentRepository.getAllotmentsverified();
		List<ClassroomTTEntity> classesList = ClassroomTTRepository.findAll();
		List<TeacherTTEntity> teacherList = TeacherTTRepository.findAll();
		*/
		
		
		
		/* 

    ////////////////////////////////////////main code /////////////////////////////////////////////////////////
            List<AllotmentEntity> labAllotments = new ArrayList<>();
			List<AllotmentEntity> theoryAllotments = new ArrayList<>();

			for (AllotmentEntity allotment : allotmentList) {
    				if ("lab".equalsIgnoreCase(allotment.getType())) {
        					labAllotments.add(allotment);
    		} else if ("theory".equalsIgnoreCase(allotment.getType())) {
        					theoryAllotments.add(allotment);
    			}
			}

        	assignLabClasses(labAllotments, classroomTimetable, teacherTimetable);
        	assignTheoryClasses(theoryAllotments, classroomTimetable, teacherTimetable);

           // Update the database with the modified timetables
           updateDatabase(classroomTimetable, teacherTimetable);
          }
//////////////////////////////////////assigning lab classes////////////////////////////////////////////////////////////
    public static void assignLabClasses(List<AllotmentEntity> labAllotments, List<ClassroomTTEntity> classroomTimetable, List<TeacherTTEntity> teacherTimetable) {
        for (AllotmentEntity allotment : labAllotments) {
            if ("lab".equalsIgnoreCase(allotment.getType())) {
                String firstAvailablePeriod = findFirstAvailablePeriod(teacherTimetable, classroomTimetable, 2);
                if (firstAvailablePeriod != null) {
                    assignPeriod(allotment, firstAvailablePeriod, teacherTimetable, classroomTimetable);
                }
            }
        }
    }
/////////////////////////////////////availability of lab classes//////////////////////////////////////////////////////////////
    
    
    public static String findAvailablePeriodForLab (List<TeacherTTEntity> teacherTimetable, List<ClassroomTTEntity> classroomTimetable, int maxPerDay) {
    List<String> commonAvailablePeriods = new ArrayList<>();

    for (String period : getAllPeriods()) {
        if (isAvailableForLab(period, teacherTimetable, maxPerDay) && isAvailableForLab(period, classroomTimetable, maxPerDay)) {
            commonAvailablePeriods.add(period);
        }
    }

    return commonAvailablePeriods.isEmpty() ? null : commonAvailablePeriods.get(0);
}

public static boolean isAvailableForLab(String period, List<TeacherTTEntity> timetable, int maxPerDay) {
    long count = timetable.stream()
            .filter(entry -> entry.getPeriods().contains(period))               ////////////////////////modification is necessary for this function//////////////////////////
            .count();

    // Check for continuous periods for lab
    return count < maxPerDay && !timetable.stream().anyMatch(entry -> entry.getPeriods().contains(getNextPeriod(period)));
}





////////////////////////////////////////////////////////assigning the theroy classes////////////////////////////////////////////////////////////////


    public static void assignTheoryClasses(List<AllotmentEntity> theoryAllotments, List<ClassroomTTEntity> classroomTimetable, List<TeacherTTEntity> teacherTimetable) {
        for (AllotmentEntity allotment : theoryAllotments) {
            if ("theory".equalsIgnoreCase(allotment.getType())) {
                String firstAvailablePeriod = findFirstAvailablePeriod(teacherTimetable, classroomTimetable, 1);

                if (firstAvailablePeriod != null) {
                    assignPeriod(allotment, firstAvailablePeriod, teacherTimetable, classroomTimetable);
                }
            }
        }
    }
/////////////////////////////////////availability of theory classes//////////////////////////////////////////////////////////////
   
public static String findAvailablePeriodForTheory(List<TeacherTTEntity> teacherTimetable, List<ClassroomTTEntity> classroomTimetable, int maxPerDay) {
    List<String> commonAvailablePeriods = new ArrayList<>();

    for (String period : getAllPeriods()) {
        if (isAvailableForTheory(period, teacherTimetable, maxPerDay) && isAvailableForTheory(period, classroomTimetable, maxPerDay)) {
            commonAvailablePeriods.add(period);
        }
    }

    return commonAvailablePeriods.isEmpty() ? null : commonAvailablePeriods.get(0);
}

public static boolean isAvailableForTheory(String period, List<TeacherTTEntity> timetable, int maxPerDay) {
    long count = timetable.stream()
            .filter(entry -> entry.getPeriods().contains(period))               ////////////////////////modification is necessary for this function//////////////////////////
            .count();

    // Check for maxPerDay constraint for theory
    return count < maxPerDay;
}

   /////////////////////////assign periods and store in the local list/////////////////////////////////////////////////

    public static void assignPeriod(AllotmentEntity allotment, String period, List<TeahcerTTEntity> teacherTimetable, List<ClassroomTTEntity> classroomTimetable) {
        // Update the teacher timetable
        updateTeacherTimetable(allotment.getTid(), period, teacherTimetable);

        // Update the classroom timetable
        updateClassroomTimetable(allotment.getCid(), period, classroomTimetable);
    }
 
    public static void updateTeacherTimetable(String id, String period, List<TimetableTTEntity> timetable) {
        TimetableEntity entity = timetable.stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (entity != null) {
            entity.getPeriods().add(period);
        }
    }
    public static void updateClassroomTimetable(String id, String period, List<ClassroomTTEntity> timetable) {
        TimetableEntity entity = timetable.stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (entity != null) {
            entity.getPeriods().add(period);
        }
    }


     ////////////////////////update in the database///////////////////////////////////////

    public static void updateDatabase(List<ClassroomTTEntity> classroomTimetable, List<TeacherTTEntity> teacherTimetable) {
        // Update the database with the modified timetables
        ClassroomTTRepository.saveAll(classroomTimetable);
        TeacherTTRepository.saveAll(teacherTimetable);
    }


}
*/
	
	
		return true;
	}
	
	
	
	
	
}
