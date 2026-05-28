package com.LMS_Project.mapper;

import org.springframework.stereotype.Component;

import com.LMS_Project.Entity.Instructor;
import com.LMS_Project.dto.request.InstructorRequest;
import com.LMS_Project.dto.response.InstructorResponse;


@Component
public class InstructorMapper {
	public Instructor toEntity(InstructorRequest request) {
        return Instructor.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .build();
    }
 
    public InstructorResponse toResponse(Instructor instructor) {
        return InstructorResponse.builder()
                .id(instructor.getId())
                .name(instructor.getName())
                .email(instructor.getEmail())
                .phone(instructor.getPhone())
                .specialization(instructor.getSpecialization())
                .createdAt(instructor.getCreatedAt())
                .updatedAt(instructor.getUpdatedAt())
                .build();
    }
 
    public void updateEntityFromRequest(Instructor instructor, InstructorRequest request) {
        instructor.setName(request.getName());
        instructor.setEmail(request.getEmail());
        instructor.setPhone(request.getPhone());
        instructor.setSpecialization(request.getSpecialization());
    }
}
