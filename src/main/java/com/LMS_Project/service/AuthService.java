package com.LMS_Project.service;

import com.LMS_Project.dto.request.LoginRequest;
import com.LMS_Project.dto.request.RegisterRequest;
import com.LMS_Project.dto.response.AuthResponse;

public interface AuthService {

	 AuthResponse register(RegisterRequest request);
	    AuthResponse login(LoginRequest request);
}
