package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.Services.AuthService;
import com.userservice.dtos.LoginRequestDto;
import com.userservice.dtos.UserDto;
import com.userservice.dtos.ValidateTokenRequestDto;
import com.userservice.dtos.logoutRequestDto;
import com.userservice.dtos.signUpRequestDto;
import com.userservice.models.SessionStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthService authservice;
	
	public AuthController(AuthService authservice) {
		this.authservice = authservice;
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){
		return authservice.login(request.getEmail(), request.getPassword());
	}
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody signUpRequestDto request){
		UserDto userDto = authservice.signup(request.getEmail(), request.getPassword());
		return new ResponseEntity<>(userDto,HttpStatus.OK);
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@RequestBody logoutRequestDto request){
		return authservice.logout(request.getToken(),request.getUserId());
	}
	
	@PostMapping("/validate")
	public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto request){
	  SessionStatus sessionStatus = authservice.validate(request.getToken(), request.getUserId());
	return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
		
	}

}
