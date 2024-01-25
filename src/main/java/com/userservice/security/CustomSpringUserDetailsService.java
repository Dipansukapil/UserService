package com.userservice.security;



import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userservice.models.User;
import com.userservice.repositories.UserRepository;

@Service
public class CustomSpringUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public CustomSpringUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<User> optionalUser =  userRepository.findByEmail(username);
		
		if(optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("User with given name is not present");
		}
		
		User user = optionalUser.get();
		
		return new CustomerUserDetails(user);
	}

}
