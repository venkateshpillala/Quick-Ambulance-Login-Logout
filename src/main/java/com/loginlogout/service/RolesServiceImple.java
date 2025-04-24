package com.loginlogout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginlogout.dto.Roles;
import com.loginlogout.exception.ResourceNotFoundException;
import com.loginlogout.feign.IRolesFeignService;

@Service
public class RolesServiceImple implements IRolesService {
	
	@Autowired
	private IRolesFeignService roleFeign;

	@Override
	public Roles getUserByUsername(String username) {
		Roles role = roleFeign.getRolesByUsername(username).getBody();
		if(role == null)
			throw new ResourceNotFoundException("RESOURCE_NOT_FOUND_WITH "+username);
		return role;
	}

}
