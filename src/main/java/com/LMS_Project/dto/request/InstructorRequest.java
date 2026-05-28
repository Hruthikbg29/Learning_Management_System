package com.LMS_Project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class InstructorRequest {
	 @NotBlank(message = "Name is required")
	    private String name;
	 
	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid email format")
	    private String email;
	 
	    @NotBlank(message = "Phone is required")
	    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	    private String phone;
	 
	    @NotBlank(message = "Specialization is required")
	    private String specialization;
}
