package com.LMS_Project.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class EnrollmentRequest {
	 @NotNull(message = "Student ID is required")
	    private Long studentId;
	 
	    @NotNull(message = "Course ID is required")
	    private Long courseId;
}
