package com.LMS_Project.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LMS_Project.Entity.Enrollment.EnrollmentStatus;
import com.LMS_Project.dto.request.EnrollmentRequest;
import com.LMS_Project.dto.response.ApiResponse;
import com.LMS_Project.dto.response.EnrollmentResponse;
import com.LMS_Project.service.EnrollmentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {
	 private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
	 
	    private final EnrollmentService enrollmentService;
	 
	    public EnrollmentController(EnrollmentService enrollmentService) {
	        this.enrollmentService = enrollmentService;
	    }
	    
	 // POST /api/enrollments
	    @PostMapping
	    public ResponseEntity<ApiResponse<EnrollmentResponse>> enrollStudent(
	            @Valid @RequestBody EnrollmentRequest request) {
	        logger.info("API request received: POST /api/enrollments - studentId: {}, courseId: {}",
	                request.getStudentId(), request.getCourseId());
	        EnrollmentResponse response = enrollmentService.enrollStudent(request);
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(ApiResponse.success("Student enrolled successfully", response));
	    }
	    
	    // GET /api/enrollments
	    @GetMapping
	    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAllEnrollments() {
	        logger.info("API request received: GET /api/enrollments");
	        List<EnrollmentResponse> response = enrollmentService.getAllEnrollments();
	        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched successfully", response));
	    }
	    
	    // GET /api/enrollments/student/{studentId}
	    @GetMapping("/student/{studentId}")
	    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getEnrollmentsByStudent(
	            @PathVariable Long studentId) {
	        logger.info("API request received: GET /api/enrollments/student/{}", studentId);
	        List<EnrollmentResponse> response = enrollmentService.getEnrollmentsByStudentId(studentId);
	        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched successfully", response));
	    }
	    
	 // GET /api/enrollments/course/{courseId}
	    @GetMapping("/course/{courseId}")
	    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getEnrollmentsByCourse(
	            @PathVariable Long courseId) {
	        logger.info("API request received: GET /api/enrollments/course/{}", courseId);
	        List<EnrollmentResponse> response = enrollmentService.getEnrollmentsByCourseId(courseId);
	        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched successfully", response));
	    }
	    

	    // GET /api/enrollments/student/{studentId}/courses  (Mandatory API #8)
	    @GetMapping("/student/{studentId}/courses")
	    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getCoursesByStudent(
	            @PathVariable Long studentId) {
	        logger.info("API request received: GET /api/enrollments/student/{}/courses", studentId);
	        List<EnrollmentResponse> response = enrollmentService.getCoursesByStudentId(studentId);
	        return ResponseEntity.ok(ApiResponse.success("Courses for student fetched successfully", response));
	    }
	    
	 // PATCH /api/enrollments/{id}/status
	    @PatchMapping("/{id}/status")
	    public ResponseEntity<ApiResponse<EnrollmentResponse>> updateEnrollmentStatus(
	            @PathVariable Long id, @RequestParam EnrollmentStatus status) {
	        logger.info("API request received: PATCH /api/enrollments/{}/status - status: {}", id, status);
	        EnrollmentResponse response = enrollmentService.updateEnrollmentStatus(id, status);
	        return ResponseEntity.ok(ApiResponse.success("Enrollment status updated successfully", response));
	    }
	    
	 // DELETE /api/enrollments/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<Void>> cancelEnrollment(@PathVariable Long id) {
	        logger.info("API request received: DELETE /api/enrollments/{}", id);
	        enrollmentService.cancelEnrollment(id);
	        return ResponseEntity.ok(ApiResponse.success("Enrollment cancelled successfully", null));
	    }
}
