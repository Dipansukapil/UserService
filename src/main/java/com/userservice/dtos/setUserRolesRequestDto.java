package com.userservice.dtos;

import java.util.List;

public class setUserRolesRequestDto {
	
	private List<Long> roleIds;

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	
	

}
