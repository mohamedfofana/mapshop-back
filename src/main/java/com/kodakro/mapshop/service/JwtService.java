package com.kodakro.mapshop.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUsername(String token);
	boolean isTokenValid(String token, UserDetails userDetails);
	String generateToken(UserDetails userDetails);
	String generateRefreshToken(UserDetails userDetails);
}
