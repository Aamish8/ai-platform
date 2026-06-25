package com.aamish.auth_service.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aamish.auth_service.dtos.RegisterRequest;
import com.aamish.auth_service.dtos.RegisterResponse;
import com.aamish.auth_service.dtos.ValidateRequest;
import com.aamish.auth_service.dtos.ValidateResponse;
import com.aamish.auth_service.model.LoginRequest;
import com.aamish.auth_service.model.LoginResponse;
import com.aamish.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	private final UserService service;
	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		return service.register(request);
	}
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return service.login(request);
	}
	@PostMapping("/validate")
	public ValidateResponse valide(@RequestBody ValidateRequest request) {
		return service.validate(request);
	}
}
