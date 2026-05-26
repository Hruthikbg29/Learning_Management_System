package com.LMS_Project.service;

import java.util.List;

import com.LMS_Project.dto.request.StudentRequest;
import com.LMS_Project.dto.response.StudentResponse;

public interface StudentService {
	StudentResponse createStudent(StudentRequest request);
    StudentResponse updateStudent(Long id, StudentRequest request);
    void deleteStudent(Long id);
    StudentResponse getStudentById(Long id);
    List<StudentResponse> getAllStudents();

}
