package com.LMS_Project.mapper;

import org.springframework.stereotype.Component;

import com.LMS_Project.Entity.Student;
import com.LMS_Project.dto.request.StudentRequest;
import com.LMS_Project.dto.response.StudentResponse;


@Component
public class StudentMapper {

	 public Student toEntity(StudentRequest request) {
	        return Student.builder()
	                .name(request.getName())
	                .email(request.getEmail())
	                .phone(request.getPhone())
	                .build();
	    }
	 
	    public StudentResponse toResponse(Student student) {
	        return StudentResponse.builder()
	                .id(student.getId())
	                .name(student.getName())
	                .email(student.getEmail())
	                .phone(student.getPhone())
	                .createdAt(student.getCreatedAt())
	                .updatedAt(student.getUpdatedAt())
	                .build();
	    }
	 
	    public void updateEntityFromRequest(Student student, StudentRequest request) {
	        student.setName(request.getName());
	        student.setEmail(request.getEmail());
	        student.setPhone(request.getPhone());
	    }
}
