package com.akul.taskslist.service;

import com.akul.taskslist.web.dto.auth.JwtRequest;
import com.akul.taskslist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
