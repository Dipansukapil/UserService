package com.userservice.Services;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.time.DateUtils;
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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

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
//			 return null;
			 throw new RuntimeException("Wrong password entered");
		 }
		 
//		 String token = RandomStringUtils.randomAlphanumeric(30);
		 // Generating token
		 //Create a test key suitable for the desired HMAC-SHA algorithm.
		 
		 MacAlgorithm alg = Jwts.SIG.HS256;
		 SecretKey key = alg.key().build();
		 
//       String message = "{\n" +
//       "  \"email\": \"harsh@scaler.com\",\n" +
//       "  \"roles\": [\n" +
//       "    \"student\",\n" +
//       "    \"ta\"\n" +
//       "  ],\n" +
//       "  \"expiry\": \"31stJan2024\"\n" +
//       "}";
		 
		 Map<String,Object> jsonMap = new HashMap<>();
		 jsonMap.put("email", user.getEmail());
		 jsonMap.put("roles", List.of(user.getRoles()));
		 jsonMap.put("createdAt", new Date());
		 jsonMap.put("expiryAt", DateUtils.addDays(new Date(), 30));
		 
		 String jws = Jwts.builder()
				 .claims(jsonMap)
				 .signWith(key,alg)
				 .compact();
		 
		 Session session = new Session();
		
		 session.setSessionStatus(SessionStatus.ACTIVE);
//		 session.setToken(token);
		 session.setToken(jws);
		 session.setUser(user);
		 sessionRepository.save(session);
		 
		 UserDto userDto = new UserDto();
		 userDto.setEmail(email);
		 
		 MultiValueMapAdapter<String, String> headers = new
				 MultiValueMapAdapter<>(new HashMap<>());
//		 headers.add(HttpHeaders.SET_COOKIE, "auth-token:"+token);
		 headers.add(HttpHeaders.SET_COOKIE, "aiuth-token:"+jws);
		
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
