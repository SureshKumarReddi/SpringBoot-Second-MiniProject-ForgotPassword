package com.suresh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suresh.service.ForgotService;

@RestController
public class ForgotController {

	@Autowired
	private ForgotService service;
	
	@PostMapping("/forgot/{email}")
	public String forgot(@PathVariable("email") String email) {
		return service.forgotPassword(email);
	}
	



}
