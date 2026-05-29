package com.LMS_Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.LMS_Project.Entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	 List<Course> findByInstructorId(Long instructorId);
	 
	    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
	    List<Course> searchByKeyword(String keyword);
}
