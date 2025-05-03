package com.loginlogout.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.loginlogout.dto.LiveLocation;


@FeignClient(name = "QUICK-AMBULANCE-DATABASE", url="http://localhost:8083")
public interface ILiveLocationFeignService {

	@PostMapping("/driver-live-location")
	public ResponseEntity<String> saveDriverLiveLocation(@RequestBody LiveLocation driverLiveLocation);
	
	@GetMapping("/driver-live-location")
	public ResponseEntity<List<LiveLocation>> getAllDriverLiveLocations();
	
	@DeleteMapping("/driver-live-location")
	public ResponseEntity<String> deleteDriverLiveLocation(String username);
	
}
