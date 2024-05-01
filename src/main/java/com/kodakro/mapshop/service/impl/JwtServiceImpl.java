package com.kodakro.mapshop.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private long jwtAccessTokenExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	@Value("${application.security.jwt.refresh-token.cookie.name}")
	private String refreshTokenCookieName;
	@Value("${application.security.jwt.access-token.cookie.name}")
	private String accessTokenCookieName;
	@Value("${application.security.jwt.refresh-token.cookie.path}")
	private String refreshTokenCookiePath;
	@Value("${application.security.jwt.access-token.cookie.path}")
	private String accessTokenCookiePath;

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, jwtAccessTokenExpiration);
	}

	@Override
	public String generateRefreshToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	}

	@Override
	public String getAccessTokenFromCookie(Cookie[] cookies) {
		return cookies != null? getJwtTokenFromCookieName(accessTokenCookieName, cookies):null;
	}

	@Override
	public String getRefreshTokenFromCookie(Cookie[] cookies) {
		
		return cookies != null? getJwtTokenFromCookieName(refreshTokenCookieName, cookies):null;
	}

	@Override
	public ResponseCookie generateAccessJwtCookieFromToken(String jwt) {
		
		return generateCookie(accessTokenCookieName, jwt, accessTokenCookiePath);
	}
	
	public ResponseCookie generateAccessJwtCookie(UserDetails customer) {
		String jwt = generateToken(customer);   
		
		return generateCookie(accessTokenCookieName, jwt, accessTokenCookiePath);
	}

	@Override
	public ResponseCookie generateRefreshJwtCookieFromToken(String refreshToken) {
		
		return generateCookie(refreshTokenCookieName, refreshToken, refreshTokenCookiePath);
	}
	
	@Override
	public ResponseCookie generateBlankAccessTokenJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(accessTokenCookieName, null).path(accessTokenCookiePath).build();

		return cookie;
	}

	@Override
	public ResponseCookie generateBlankJwtRefreshCookie() {
		ResponseCookie cookie = ResponseCookie.from(refreshTokenCookieName, null).path(refreshTokenCookiePath).build();
		
		return cookie;
	}

	private ResponseCookie generateCookie(String name, String value, String path) {
		ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(jwtAccessTokenExpiration).httpOnly(true).build();
		return cookie;
	}

	public String getJwtTokenFromCookieName(String name, Cookie cookies[]) {
		for (Cookie cookie : cookies) {
			if(name.equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);

		return claimsResolvers.apply(claims);
	}

	private Claims extractAllClaims(String token) {

		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();

	}

	private Key getSignInKey() {
		byte[] mysecretKey = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(mysecretKey);
	}


}
