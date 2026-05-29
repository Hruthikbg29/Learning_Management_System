package com.LMS_Project.dto.response;

import java.time.LocalDateTime;

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
public class CourseResponse {

	 private Long id;
	    private String title;
	    private String description;
	    private String duration;
	    private Integer maxStudents;
	    private Long instructorId;
	    private String instructorName;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
