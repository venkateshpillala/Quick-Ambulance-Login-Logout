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
	public ResponseEntity<Roles> loginSuccess(@RequestBody Roles roles ,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("roles", roles);

		if (roles.getRole().equalsIgnoreCase("DRIVER")) {
			driverService.updateDriverStatus(roles.getUsername(), true);
			driverLogsService.saveDriverLoginDetails(roles.getUsername());
		}

		return new ResponseEntity<Roles>(roles, HttpStatus.OK);
	}

	@GetMapping("/logout-success")
	public ResponseEntity<Map<String, String>> logoutSuccess(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> result = new HashMap<String, String>();
		HttpSession session = request.getSession(false);
		Roles roles = (Roles) session.getAttribute("roles");

		if (roles.getRole().equalsIgnoreCase("DRIVER")) {
			driverService.updateDriverStatus(roles.getUsername(), false);
			driverLogsService.logoutDriver(roles.getUsername());
			ambulanceService.updateAmbulanceStatus(
					driverLogsService.getDriverLogsByUsernameAndDate(roles.getUsername(), LocalDate.now())
							.getAmbulance().getVehicleNumber(),
					false);
			liveLocationService.deleteDriverLiveLocation(roles.getUsername());
		}

		Cookie cookie = new Cookie("JSESSIONID", "");
		cookie.setPath("/"); // must match the original path
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0); // tells browser to delete it
		response.addCookie(cookie);

		if (session != null) {
			result.put("username", roles.getUsername());
			result.put("role", roles.getRole());
			result.put("logout", "success");

			session.invalidate();
		}
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	@PostMapping("/update-vehicle")
	public ResponseEntity<String> updateVehicle(HttpServletRequest request, @RequestParam String vehicleNumber) {
		HttpSession session = request.getSession(false);
		Roles role = (Roles) session.getAttribute("roles");
		driverLogsService.updateVehicle(role.getUsername(), vehicleNumber);
		ambulanceService.updateAmbulanceStatus(vehicleNumber.toUpperCase(), true);
		return new ResponseEntity<String>(role.getUsername(), HttpStatus.OK);
	}

}
