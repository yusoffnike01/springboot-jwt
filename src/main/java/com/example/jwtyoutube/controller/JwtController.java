package com.example.jwtyoutube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtyoutube.entity.JwtRequest;
import com.example.jwtyoutube.entity.JwtResponse;
import com.example.jwtyoutube.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping({"/login"})
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception  {
		return jwtService.createJwtToken(jwtRequest);
	}
}
