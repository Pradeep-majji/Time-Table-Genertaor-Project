package com.demo.timetable.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.timetable.entity.ClassroomTTEntity;

public interface ClassroomTTRepository extends JpaRepository<ClassroomTTEntity,String>{

}
