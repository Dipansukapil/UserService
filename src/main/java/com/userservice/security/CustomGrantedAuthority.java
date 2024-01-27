package com.userservice.security;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.models.Role;

import lombok.NoArgsConstructor;

@JsonDeserialize(as = CustomGrantedAuthority.class)
public class CustomGrantedAuthority implements GrantedAuthority{

	private Role role;
	
    public CustomGrantedAuthority() {
    	
    }
    
    public CustomGrantedAuthority(Role role) {
        this.role = role;
    }
	
	@Override
	@JsonIgnore
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role.getRole();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
