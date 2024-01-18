package com.userservice.Services;

import org.springframework.stereotype.Service;

import com.userservice.models.Role;
import com.userservice.repositories.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public Role createRole(String name) {
		Role role = new Role();
		role.setRole(name);
		
		return roleRepository.save(role);
	}

}
