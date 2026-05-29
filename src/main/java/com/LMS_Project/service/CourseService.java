package com.LMS_Project.service;

import java.util.List;

import com.LMS_Project.dto.request.CourseRequest;
import com.LMS_Project.dto.response.CourseResponse;

public interface CourseService {
	CourseResponse createCourse(CourseRequest request);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    List<CourseResponse> getCoursesByInstructorId(Long instructorId);
    List<CourseResponse> searchCourses(String keyword);
}
