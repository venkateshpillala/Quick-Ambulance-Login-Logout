package com.loginlogout.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginlogout.dto.Driver;


@FeignClient(name = "QUICK-AMBULANCE-DATABASE", url="https://quick-ambulance-database.onrender.com")
public interface IDriverFeignService {
	@GetMapping("/driver")
	public ResponseEntity<Driver> findByUsername(@RequestParam String username);
	
	@PostMapping("/driver/update-status")
	public ResponseEntity<String> updateDriverStatus(@RequestParam String username,
													@RequestParam boolean status);

	
}
