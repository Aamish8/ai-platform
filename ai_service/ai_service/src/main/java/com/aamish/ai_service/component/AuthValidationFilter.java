package com.aamish.ai_service.component;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aamish.ai_service.dtos.ValidateRequest;
import com.aamish.ai_service.dtos.ValidateResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthValidationFilter extends OncePerRequestFilter{
	private final RestTemplate restTemplate;
	@Value("${auth.service.url}")
	private String authServiceUrl;
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
            String auth=request.getHeader("Authorization");
            if(auth==null||!auth.startsWith("Bearer ")) {
            	filterChain.doFilter(request,response);
            	return;
            }
            String token=auth.substring(7);
            ValidateRequest request1=new ValidateRequest();
            request1.setToken(token);
            ValidateResponse response1 = restTemplate.postForObject(
            	    authServiceUrl + "/api/auth/validate",
            	    request1,
            	    ValidateResponse.class
            	);
            if(response1==null||!response1.isValid()) {
            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            	return;
            }
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken
            		(response1.getEmail(),null,Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
	}

}
