package com.LMS_Project.service;

import java.util.List;

import com.LMS_Project.dto.request.InstructorRequest;
import com.LMS_Project.dto.response.InstructorResponse;

public interface InstructorService {
	InstructorResponse createInstructor(InstructorRequest request);
    InstructorResponse updateInstructor(Long id, InstructorRequest request);
    void deleteInstructor(Long id);
    InstructorResponse getInstructorById(Long id);
    List<InstructorResponse> getAllInstructors();
}
