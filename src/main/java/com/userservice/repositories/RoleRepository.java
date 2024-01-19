package com.userservice.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	
	List<Role> findAllByIdIn(List<Long> roleIds);

}
