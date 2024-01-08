package com.userservice.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.models.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
	
	List<Role> findAllByIdIn(List<Long> roleIds);

}
