package com.aamish.auth_service.service;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
	@org.springframework.beans.factory.annotation.Value("${jwt.secret}")
	private String secret;
	@org.springframework.beans.factory.annotation.Value("${jwt.expiration}")
	private long expiration;
	public String generateToken(String email) {
		return Jwts.builder()
		.subject(email)
		.issuedAt(new Date(expiration))
		.expiration(new Date(System.currentTimeMillis()+expiration))
		 .signWith(getSignKey())
		 .compact();
	}
	public SecretKey getSignKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	public String extractEmail(String token) {
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	public boolean isTokenValid(String token, String email) {
	    return email.equals(extractEmail(token))
	            && !extractExpiration(token).before(new Date());
	}
	public Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration();
	}
}
