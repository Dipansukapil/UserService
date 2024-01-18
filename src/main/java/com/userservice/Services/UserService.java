package com.userservice.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.userservice.dtos.UserDto;
import com.userservice.models.Role;
import com.userservice.models.User;
import com.userservice.repositories.RoleRepository;
import com.userservice.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	UserService(UserRepository userRepository,RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	public UserDto getUserDetails(Long userId) {
		
		Optional<User> userOptional = userRepository.findById(userId);
	    
		if(userOptional.isEmpty()) {
			return null;
		}
		
		return UserDto.from(userOptional.get());
	}
	
	public UserDto setUserRoles(Long userId,List<Long> roleIds) {
		
		Optional<User> userOptional = userRepository.findById(userId);
		List<Role> roles = roleRepository.findAllByIdIn(roleIds);
		
		if(userOptional.isEmpty()) {
			return null;
		}
		
		User user = userOptional.get();
		user.setRoles(Set.copyOf(roles));
		
		User savedUser = userRepository.save(user);
		
		return UserDto.from(savedUser);
	}
	

}
