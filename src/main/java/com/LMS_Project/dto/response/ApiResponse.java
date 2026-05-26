package com.LMS_Project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

	  private String message;
	    private T data;
	    private Object errors;
	 
	    public static <T> ApiResponse<T> success(String message, T data) {
	        return ApiResponse.<T>builder()
	                .message(message)
	                .data(data)
	                .build();
	    }
	 
	    public static <T> ApiResponse<T> error(String message, Object errors) {
	        return ApiResponse.<T>builder()
	                .message(message)
	                .errors(errors)
	                .build();
	    }
}
