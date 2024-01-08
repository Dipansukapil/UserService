package com.userservice.dtos;

import java.util.HashSet;
import java.util.Set;

import com.userservice.models.Role;
import com.userservice.models.User;

public class UserDto {
	
	private String email;
	private Set<Role> roles = new HashSet<>();
	
	
	
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public static UserDto from(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setRoles(user.getRoles());
		
		return userDto;
		
	}

}
