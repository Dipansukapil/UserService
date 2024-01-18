package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.Services.RoleService;
import com.userservice.dtos.createRoleRequestDto;
import com.userservice.models.Role;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	private RoleService roleservice;
	
	RoleController(RoleService roleservice){
		this.roleservice = roleservice;
	}
	
	@PostMapping
	public ResponseEntity<Role> createRole(createRoleRequestDto request){
		Role role = roleservice.createRole(request.getName());
		
		return new ResponseEntity<>(role,HttpStatus.OK);
	}
}
