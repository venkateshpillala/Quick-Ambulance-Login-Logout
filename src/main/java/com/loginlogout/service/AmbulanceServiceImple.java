package com.loginlogout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginlogout.feign.IAmbulanceFeignService;

@Service
public class AmbulanceServiceImple implements IAmbulanceService{
	
	@Autowired
	IAmbulanceFeignService ambulanceFeign;

	@Override
	public String updateAmbulanceStatus(String vehicleNumber, Boolean status) {
		String ambulance = ambulanceFeign.updateAmbulanceStatus(vehicleNumber, false).getBody();
		if(ambulance == null)
			throw new RuntimeException("STATUS_UPDATION_FAILED_WITH_VEHICLE_NUMBER "+vehicleNumber);
		return ambulance;
	}

}
