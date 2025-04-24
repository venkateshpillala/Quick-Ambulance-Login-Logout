package com.loginlogout.service;

import java.time.LocalDate;

import com.loginlogout.dto.DriverLogs;

public interface IDriverLogsService {
	
	public Long saveDriverLoginDetails(String username); 
	public Long logoutDriver(String username);
	public DriverLogs getDriverLogsByUsernameAndDate(String username, LocalDate date);
	public Integer updateVehicle(String username, String vehicleNumber);

}
