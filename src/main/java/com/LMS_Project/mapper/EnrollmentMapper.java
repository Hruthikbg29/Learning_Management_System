package com.LMS_Project.mapper;

import org.springframework.stereotype.Component;

import com.LMS_Project.Entity.Enrollment;
import com.LMS_Project.dto.response.EnrollmentResponse;

@Component
public class EnrollmentMapper {
	public EnrollmentResponse toResponse(Enrollment enrollment) {
		return EnrollmentResponse.builder().id(enrollment.getId()).studentId(enrollment.getStudent().getId())
				.studentName(enrollment.getStudent().getName()).courseId(enrollment.getCourse().getId())
				.courseTitle(enrollment.getCourse().getTitle()).status(enrollment.getStatus())
				.enrolledAt(enrollment.getEnrolledAt()).updatedAt(enrollment.getUpdatedAt()).build();
	}
}
