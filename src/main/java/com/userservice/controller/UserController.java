package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.Services.UserService;
import com.userservice.dtos.UserDto;
import com.userservice.dtos.setUserRolesRequestDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	UserController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
		
		UserDto userDto = userService.getUserDetails(userId);
		
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	@PostMapping("/{id}/roles")
	public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId,@RequestBody setUserRolesRequestDto request){
		
		UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());
		
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}

}
