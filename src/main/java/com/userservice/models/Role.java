package com.userservice.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;

@Entity
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel{
	
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}
