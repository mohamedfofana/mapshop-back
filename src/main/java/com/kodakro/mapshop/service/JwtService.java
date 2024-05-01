package com.kodakro.mapshop.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.http.Cookie;

public interface JwtService {
	String extractUsername(String token);
	boolean isTokenValid(String token, UserDetails userDetails);
	String generateToken(UserDetails userDetails);
	String generateRefreshToken(UserDetails userDetails);
	String getAccessTokenFromCookie(Cookie cookies[]);
	String getRefreshTokenFromCookie(Cookie cookies[]);
	ResponseCookie generateAccessJwtCookieFromToken(String jwt);
	ResponseCookie generateRefreshJwtCookieFromToken(String refreshToken);
	ResponseCookie generateBlankAccessTokenJwtCookie();
	ResponseCookie generateBlankJwtRefreshCookie();
}
