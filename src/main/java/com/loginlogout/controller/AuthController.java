package com.loginlogout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loginlogout.dto.Roles;
import com.loginlogout.service.IAmbulanceService;
import com.loginlogout.service.IDriverLogsService;
import com.loginlogout.service.IDriverService;
import com.loginlogout.service.ILiveLocationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IDriverLogsService driverLogsService;

	@Autowired
	private IAmbulanceService ambulanceService;

	@Autowired
	private ILiveLocationService liveLocationService;

	@PostMapping("/login-success")
	public ResponseEntity<Roles> loginSuccess(@RequestBody Roles roles, HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.setAttribute("roles", roles);

		if (roles.getRole().equalsIgnoreCase("DRIVER")) {
			driverService.updateDriverStatus(roles.getUsername(), true);
			driverLogsService.saveDriverLoginDetails(roles.getUsername());
		}

		return new ResponseEntity<Roles>(roles, HttpStatus.OK);
	}

	@GetMapping("/logout-success")
	public ResponseEntity<Map<String, String>> logoutSuccess(@RequestBody Roles role, 
			HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();

		if (role.getRole().equalsIgnoreCase("DRIVER")) {
			driverService.updateDriverStatus(role.getUsername(), false);
			driverLogsService.logoutDriver(role.getUsername());
			ambulanceService.updateAmbulanceStatus(
					driverLogsService.getDriverLogsByUsernameAndDate(role.getUsername(), LocalDate.now()).getAmbulance()
							.getVehicleNumber(),
					false);
			liveLocationService.deleteDriverLiveLocation(role.getUsername());
		}

		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setPath("/"); // must match the original path
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0); // tells browser to delete it
		response.addCookie(cookie);

		result.put("username", role.getUsername());
		result.put("role", role.getRole());
		result.put("logout", "success");
		request.getSession(false).invalidate();
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	@PostMapping("/update-vehicle")
	public ResponseEntity<String> updateVehicle(@RequestParam String username, @RequestParam String vehicleNumber) {
		// HttpSession session = request.getSession(false);
		// Roles role = (Roles) session.getAttribute("roles");
		driverLogsService.updateVehicle(username, vehicleNumber);
		ambulanceService.updateAmbulanceStatus(vehicleNumber.toUpperCase(), true);
		return new ResponseEntity<String>(username, HttpStatus.OK);
	}

}
