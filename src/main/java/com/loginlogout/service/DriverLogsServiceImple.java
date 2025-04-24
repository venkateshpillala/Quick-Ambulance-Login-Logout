package com.loginlogout.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loginlogout.dto.DriverLogs;
import com.loginlogout.exception.ResourceNotFoundException;
import com.loginlogout.feign.IDriverLogsFeignService;

@Service
public class DriverLogsServiceImple implements IDriverLogsService {
	
	@Autowired
	private IDriverLogsFeignService driverFeign;

	@Override
	public Long saveDriverLoginDetails(String username) {
		Long status =  driverFeign.newLogin(username).getBody();
		if(status == null)
			throw new RuntimeException("SAVE_DRIVER_LOGS_FAILED "+username);
		return status;
	}

	@Override
	public Long logoutDriver(String username) {
		Long status = driverFeign.updateLogout(username).getBody();
		if(status == null)
			throw new RuntimeException("LOG_OUT_FAILED "+username);
		return status;
	}

	@Override
	public DriverLogs getDriverLogsByUsernameAndDate(String username, LocalDate date) {
		DriverLogs dl = driverFeign.getDriverLogsByUsernameAndDate(username, date).getBody();
		if(dl == null)
			throw new ResourceNotFoundException("DRIVER_LOGS_NOT_FOUND "+username);
		return dl;

	}

	@Override
	public Integer updateVehicle(String username, String vehicleNumber) {
		Integer status = driverFeign.updateVehicleNumber(username, vehicleNumber).getBody();
		if(status == null)
			throw new RuntimeException("VEHICLE_NOT_UPDATED "+username);
		return status;
	}

	
	

}
