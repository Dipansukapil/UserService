package com.userservice.Services;

import org.springframework.stereotype.Service;

import com.userservice.repositories.SessionRepository;
import com.userservice.repositories.UserRepository;

@Service
public class AuthService {
	
	private UserRepository userRepository;
	private SessionRepository sessionRepository;
	
	public AuthService(UserRepository userRepository,SessionRepository sessionRepository) {
		this.userRepository = userRepository;
		this.sessionRepository = sessionRepository;
	}
	
	

}
