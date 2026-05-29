package com.LMS_Project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LMS_Project.dto.request.CourseRequest;
import com.LMS_Project.dto.response.ApiResponse;
import com.LMS_Project.dto.response.CourseResponse;
import com.LMS_Project.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	 
    private final CourseService courseService;
 
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
 // POST /api/courses
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
            @Valid @RequestBody CourseRequest request) {
        logger.info("API request received: POST /api/courses");
        CourseResponse response = courseService.createCourse(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Course created successfully", response));
    }
    
    // PUT /api/courses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        logger.info("API request received: PUT /api/courses/{}", id);
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(ApiResponse.success("Course updated successfully", response));
    }
    
 // DELETE /api/courses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        logger.info("API request received: DELETE /api/courses/{}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success("Course deleted successfully", null));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        logger.info("API request received: GET /api/courses/{}", id);
        CourseResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(ApiResponse.success("Course fetched successfully", response));
    }
    
    // GET /api/courses
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        logger.info("API request received: GET /api/courses");
        List<CourseResponse> response = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success("Courses fetched successfully", response));
    }
    

    // GET /api/courses/instructor/{instructorId}
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByInstructor(
            @PathVariable Long instructorId) {
        logger.info("API request received: GET /api/courses/instructor/{}", instructorId);
        List<CourseResponse> response = courseService.getCoursesByInstructorId(instructorId);
        return ResponseEntity.ok(ApiResponse.success("Courses fetched successfully", response));
    }
    
    // GET /api/courses/search?keyword=java
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchCourses(
            @RequestParam String keyword) {
        logger.info("API request received: GET /api/courses/search?keyword={}", keyword);
        List<CourseResponse> response = courseService.searchCourses(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search results fetched successfully", response));
    }
}
