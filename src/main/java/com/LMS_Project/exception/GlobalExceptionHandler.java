package com.LMS_Project.exception;

import java.util.Map;
import java.util.HashMap;
import com.LMS_Project.dto.response.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
	        logger.error("Resource not found: {}", ex.getMessage());
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(ApiResponse.error(ex.getMessage(), null));
	    }
	 
	    @ExceptionHandler(DuplicateResourceException.class)
	    public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(DuplicateResourceException ex) {
	        logger.error("Duplicate resource: {}", ex.getMessage());
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body(ApiResponse.error(ex.getMessage(), null));
	    }
	 
	    @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
	        logger.error("Bad request: {}", ex.getMessage());
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ApiResponse.error(ex.getMessage(), null));
	    }
	 
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        logger.error("Validation failed: {}", errors);
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ApiResponse.error("Validation failed", errors));
	    }
	 
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
	        logger.error("Unexpected error: {}", ex.getMessage(), ex);
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(ApiResponse.error("An unexpected error occurred", null));
	    }
}
