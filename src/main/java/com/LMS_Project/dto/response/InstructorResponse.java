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
public class InstructorResponse {
	private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
