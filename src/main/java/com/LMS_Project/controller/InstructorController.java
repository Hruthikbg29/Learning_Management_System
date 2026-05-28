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
import org.springframework.web.bind.annotation.RestController;

import com.LMS_Project.dto.request.InstructorRequest;
import com.LMS_Project.dto.response.ApiResponse;
import com.LMS_Project.dto.response.InstructorResponse;
import com.LMS_Project.service.InstructorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/instructors")
@CrossOrigin(origins = "*")
public class InstructorController {
	  private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);
	  
	  private final InstructorService instructorService;
	  
	    public InstructorController(InstructorService instructorService) {
	        this.instructorService = instructorService;
	    }
	    
	    
	    // POST /api/instructors
	    @PostMapping
	    public ResponseEntity<ApiResponse<InstructorResponse>> createInstructor(@Valid @RequestBody InstructorRequest request) {
	        logger.info("API request received: POST /api/instructors");
	        InstructorResponse response = instructorService.createInstructor(request);
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(ApiResponse.success("Instructor created successfully", response));
	    }
	    
	    // PUT /api/instructors/{id}
	    @PutMapping("/{id}")
	    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(
	            @PathVariable Long id,
	            @Valid @RequestBody InstructorRequest request) {
	        logger.info("API request received: PUT /api/instructors/{}", id);
	        InstructorResponse response = instructorService.updateInstructor(id, request);
	        return ResponseEntity.ok(ApiResponse.success("Instructor updated successfully", response));
	    }
	    
	 // DELETE /api/instructors/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<ApiResponse<Void>> deleteInstructor(@PathVariable Long id) {
	        logger.info("API request received: DELETE /api/instructors/{}", id);
	        instructorService.deleteInstructor(id);
	        return ResponseEntity.ok(ApiResponse.success("Instructor deleted successfully", null));
	    }
	    
	    // GET /api/instructors/{id}
	    @GetMapping("/{id}")
	    public ResponseEntity<ApiResponse<InstructorResponse>> getInstructorById(@PathVariable Long id) {
	        logger.info("API request received: GET /api/instructors/{}", id);
	        InstructorResponse response = instructorService.getInstructorById(id);
	        return ResponseEntity.ok(ApiResponse.success("Instructor fetched successfully", response));
	    }
	    
	 // GET /api/instructors
	    @GetMapping
	    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getAllInstructors() {
	        logger.info("API request received: GET /api/instructors");
	        List<InstructorResponse> response = instructorService.getAllInstructors();
	        return ResponseEntity.ok(ApiResponse.success("Instructors fetched successfully", response));
	    }
}
