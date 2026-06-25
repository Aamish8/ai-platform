package com.aamish.auth_service.service;
import java.util.Date;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.aamish.auth_service.dtos.RegisterRequest;
import com.aamish.auth_service.dtos.RegisterResponse;
import com.aamish.auth_service.dtos.ValidateRequest;
import com.aamish.auth_service.dtos.ValidateResponse;
import com.aamish.auth_service.model.LoginRequest;
import com.aamish.auth_service.model.LoginResponse;
import com.aamish.auth_service.model.User;
import com.aamish.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository repo;
	private final BCryptPasswordEncoder pe;
	private final JwtService jwtService;
  public RegisterResponse register(RegisterRequest request) {
	    Optional<User> user=repo.findByEmail(request.getEmail());
	    if(!user.isEmpty())throw new RuntimeException("User already exists");
	     User savedUser =repo.save(User.builder()
	     .email(request.getEmail())
	     .name(request.getName())
	     .password(pe.encode(request.getPassword()))
	     .build());
	     return  RegisterResponse.builder()
	    		 .name(request.getName())
	    		 .email(request.getEmail())
	    		 .id(savedUser.getId())
	    		 .build();
  }
  public LoginResponse login(LoginRequest request) {

	    User user = repo.findByEmail(request.getEmail())
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    if (!pe.matches(request.getPassword(),user.getPassword())) {
	        throw new RuntimeException("Invalid credentials");
	    }
	    String token=jwtService.generateToken(user.getEmail());
	    
	    return LoginResponse.builder()
	    		.token(token)
	    		.build();
  }
  public ValidateResponse validate(ValidateRequest request) {
	  System.out.println(request.getToken());
	  try {
	 String email=jwtService.extractEmail(request.getToken());
	    if(jwtService.extractExpiration(request.getToken()).before(new Date())) {
	    	throw new RuntimeException("Token has expired");
	    }
	    return ValidateResponse.builder()
	    		.email(email)
	    		.valid(true)
	    		.build();
	  }
	  catch(Exception e) {
		  return ValidateResponse.builder()
				  .email(null)
				  .valid(false)
				  .build();
	  }
  }
}
