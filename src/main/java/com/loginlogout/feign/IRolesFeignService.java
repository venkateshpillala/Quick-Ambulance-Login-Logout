package com.loginlogout.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginlogout.dto.Roles;


@FeignClient(name="QUICK-AMBULANCE-DATABASE")
public interface IRolesFeignService {

	@GetMapping("/roles")
	public ResponseEntity<Roles> getRolesByUsername(@RequestParam String username);
	
}
