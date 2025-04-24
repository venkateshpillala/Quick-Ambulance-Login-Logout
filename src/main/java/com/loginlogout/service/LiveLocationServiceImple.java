package com.loginlogout.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginlogout.feign.ILiveLocationFeignService;

@Service
public class LiveLocationServiceImple implements ILiveLocationService {
	
	@Autowired
	private ILiveLocationFeignService feignService;

	@Override
	public String deleteDriverLiveLocation(String username) {
		String status = feignService.deleteDriverLiveLocation(username).getBody();
		if(status != null)
			throw new RuntimeException("LIVE_LOCATION_NOT_DELETED "+username);
		return status;
	}
	
	
	
}
