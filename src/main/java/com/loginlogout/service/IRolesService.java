package com.loginlogout.service;

import com.loginlogout.dto.Roles;

public interface IRolesService {

	Roles getUserByUsername(String username);
}
