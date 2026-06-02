package com.LMS_Project.service;

import java.util.List;

import com.LMS_Project.Entity.Enrollment.EnrollmentStatus;
import com.LMS_Project.dto.request.EnrollmentRequest;
import com.LMS_Project.dto.response.EnrollmentResponse;

public interface EnrollmentService {
	EnrollmentResponse enrollStudent(EnrollmentRequest request);
    EnrollmentResponse updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status);
    void cancelEnrollment(Long enrollmentId);
    List<EnrollmentResponse> getEnrollmentsByStudentId(Long studentId);
    List<EnrollmentResponse> getEnrollmentsByCourseId(Long courseId);
    List<EnrollmentResponse> getCoursesByStudentId(Long studentId);
    List<EnrollmentResponse> getAllEnrollments();
}
