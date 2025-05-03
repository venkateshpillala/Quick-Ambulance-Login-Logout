package com.loginlogout.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginlogout.dto.Ambulance;


@FeignClient(name = "QUICK-AMBULANCE-DATABASE", url="http://localhost:8083")
public interface IAmbulanceFeignService {
	@GetMapping("/ambulance")
	public ResponseEntity<Ambulance> findVehicleByVehicleNumber(@RequestParam String vehicleNumber);
	
	@PostMapping("/ambulance/update-status")
	public ResponseEntity<String> updateAmbulanceStatus(@RequestParam String vehicleNumber, 
														@RequestParam boolean status);
	
	
}
