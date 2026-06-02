package com.LMS_Project.dto.response;

import java.time.LocalDateTime;

import com.LMS_Project.Entity.Enrollment.EnrollmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {
	  private Long id;
	    private Long studentId;
	    private String studentName;
	    private Long courseId;
	    private String courseTitle;
	    private EnrollmentStatus status;
	    private LocalDateTime enrolledAt;
	    private LocalDateTime updatedAt;
}
