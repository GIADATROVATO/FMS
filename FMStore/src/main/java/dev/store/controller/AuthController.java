package dev.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.store.dto.LoginRequest;
import dev.store.services.JwtService;
import dev.store.services.UserService;


@RestController
@RequestMapping("/auth")

public class AuthController {
	private final JwtService jwtService;
	private final UserService userService;

	public AuthController(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService=userService;
	}
	/*
	 * didattico 
	 * 
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String user, @RequestParam String pass){
		if(user.equals("admin")&& pass.equals(1234)) {
			String token= jwtService.generateToken(user, "ADMIN");
			return ResponseEntity.ok(token);
		}
		if(user.equals("user")&& pass.equals(1234)) {
			String token= jwtService.generateToken(user, "USER");
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(401).body("login fallito");
	}
	*/
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest req){
		 return userService.authenticate(req.getUsername(), req.getPassword())
		            .map(user -> {
		                String token = jwtService.generateToken( user.getUsername(), user.getRole());
		                return ResponseEntity.ok(token);
		            })
		            .orElse(ResponseEntity.status(401).build());
					
	}
}
