package com.loginlogout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginlogout.feign.IDriverFeignService;
@Service
public class DriverServiceImple implements IDriverService {
	
	@Autowired
	private IDriverFeignService driverFeign;

	@Override
	public String updateDriverStatus(String username, Boolean status) {
		String stat = driverFeign.updateDriverStatus(username, status).getBody();
		if(stat == null)
			throw new RuntimeException("DRIVER_STATUS_NOT_UPDATED "+username);
		return stat;
	}


}
