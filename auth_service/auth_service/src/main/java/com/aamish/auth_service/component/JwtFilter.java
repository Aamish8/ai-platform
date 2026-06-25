package com.aamish.auth_service.component;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aamish.auth_service.repository.UserRepository;
import com.aamish.auth_service.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter{
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		String auth=request.getHeader("Authorization");
		if(auth==null||!auth.startsWith("Bearer ")) {
		 filterChain.doFilter(request, response);
		 return;
		}
		String token=auth.substring(7);
		String email=jwtService.extractEmail(token);
		if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=userDetailsService.loadUserByUsername(email);
			if(jwtService.isTokenValid(token,userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
						
			}	
		}
		filterChain.doFilter(request, response);
	}
  
}
