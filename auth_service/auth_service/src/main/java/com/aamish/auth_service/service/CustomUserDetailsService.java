package com.aamish.auth_service.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.aamish.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
   private final UserRepository repo;
   public UserDetails loadUserByUsername(String email) {
	   com.aamish.auth_service.model.User user=repo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
	   return User.builder()
			   .username(user.getEmail())
			   .password(user.getPassword())
			   .authorities("USER")
			   .build();
   }
}
