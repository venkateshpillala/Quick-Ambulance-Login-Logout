package com.loginlogout.feign;


import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginlogout.dto.DriverLogs;


@FeignClient(name = "QUICK-AMBULANCE-DATABASE", url="http://localhost:8083")
public interface IDriverLogsFeignService {

	@PostMapping("/driver-logs/login")
	public ResponseEntity<Long> newLogin(@RequestParam String username);
	
	@PostMapping("/driver-logs/logout")
	public ResponseEntity<Long> updateLogout(@RequestParam String username);
	
	@GetMapping("/driver-logs/username-date")
	public ResponseEntity<DriverLogs> getDriverLogsByUsernameAndDate(@RequestParam String username, @RequestParam LocalDate date);
	
	@PostMapping("/driver-logs/update-vehicle")
	public ResponseEntity<Integer> updateVehicleNumber(@RequestParam String username, 
													@RequestParam String vehicleNumber);
	
}
