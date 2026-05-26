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

import com.LMS_Project.dto.request.StudentRequest;
import com.LMS_Project.dto.response.ApiResponse;
import com.LMS_Project.dto.response.StudentResponse;
import com.LMS_Project.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	 
    private final StudentService studentService;
 
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
 // POST /api/students
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody StudentRequest request) {
        logger.info("API request received: POST /api/students");
        StudentResponse response = studentService.createStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created successfully", response));
    }
    

    // PUT /api/students/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        logger.info("API request received: PUT /api/students/{}", id);
        StudentResponse response = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", response));
    }
    
    // DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        logger.info("API request received: DELETE /api/students/{}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully", null));
    }
    
 // GET /api/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        logger.info("API request received: GET /api/students/{}", id);
        StudentResponse response = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success("Student fetched successfully", response));
    }
    
    // GET /api/students
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        logger.info("API request received: GET /api/students");
        List<StudentResponse> response = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success("Students fetched successfully", response));
    }
}
