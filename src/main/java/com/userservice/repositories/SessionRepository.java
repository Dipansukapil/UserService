package com.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.models.Session;


public interface SessionRepository extends JpaRepository<Session, String> {
	
	Optional<Session> findByTokenAndUser_Id(String token, Long userId);

}
