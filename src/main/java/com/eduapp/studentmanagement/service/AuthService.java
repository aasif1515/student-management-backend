package com.eduapp.studentmanagement.service;

import com.eduapp.studentmanagement.dto.LoginRequest;
import com.eduapp.studentmanagement.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
