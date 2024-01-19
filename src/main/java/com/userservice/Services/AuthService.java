package com.userservice.Services;


import java.util.HashMap;
import java.util.Optional;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMapAdapter;


import com.userservice.models.User;
//import com.nimbusds.jose.Header;
import com.userservice.dtos.UserDto;
import com.userservice.models.Session;
import com.userservice.models.SessionStatus;
import com.userservice.repositories.SessionRepository;
import com.userservice.repositories.UserRepository;

@Service
public class AuthService {
	
	private UserRepository userRepository;
	private SessionRepository sessionRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AuthService(UserRepository userRepository,SessionRepository sessionRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.sessionRepository = sessionRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public ResponseEntity<UserDto> login(String email, String password){
		
		 Optional<User> userOptional = userRepository.findByEmail(email);
		
		 if(userOptional.isEmpty()) {
			 return null;
		 }
		 
		 User user = userOptional.get();
		 
//		 if(!user.getPassword().equals(password)) {
//			 return null;
//		 }
		 
		 if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
			 return null;
		 }
		 
		 String token = RandomStringUtils.randomAlphanumeric(30);
		 
		 Session session = new Session();
		 session.setSessionStatus(SessionStatus.ACTIVE);
		 session.setToken(token);
		 session.setUser(user);
		 sessionRepository.save(session);
		 
		 UserDto userDto = new UserDto();
		 userDto.setEmail(email);
		 
		 MultiValueMapAdapter<String, String> headers = new
				 MultiValueMapAdapter<>(new HashMap<>());
		 headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+token);
		
		 ResponseEntity<UserDto> response = new ResponseEntity<>(userDto,headers,HttpStatus.OK);
		 
		 return response;
		
	}
	
	public UserDto signup(String email,String password) {
		
		User user = new User();
		user.setEmail(email);
//		user.setPassword(password);
		user.setPassword(bCryptPasswordEncoder.encode(password));
		
		User savedUser = userRepository.save(user);
		
		return UserDto.from(savedUser);
	}
	
	public ResponseEntity<Void> logout(String token,Long userId){
		
		Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
		
		if(sessionOptional.isEmpty()) {
			return null;
		}
		
		Session session =  sessionOptional.get();
		
		session.setSessionStatus(SessionStatus.ENDED);
		sessionRepository.save(session);
		
		return ResponseEntity.ok().build();
	}
	
	public SessionStatus validate(String token,Long userId) {
		Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);
		
		if(sessionOptional.isEmpty()) {
			return null;
		}
		
		return SessionStatus.ACTIVE;
	}

}
