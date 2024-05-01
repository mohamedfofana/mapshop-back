package com.kodakro.mapshop.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.service.AuthService;
import com.kodakro.mapshop.service.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody Customer requestCustomer) {
		return ResponseEntity.ok(authService.register(requestCustomer));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestCustomer) {
		final AuthenticationResponseDTO authResponse = authService.login(requestCustomer);
		
		return ResponseEntity.ok()
					  .header(HttpHeaders.SET_COOKIE, jwtService.generateAccessJwtCookieFromToken(authResponse.getAccessToken()).toString())
					  .header(HttpHeaders.SET_COOKIE, jwtService.generateRefreshJwtCookieFromToken(authResponse.getRefreshToken()).toString())
					  .body(authResponse);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {
		final String newJwtAccessToken = authService.refreshToken(request);
		
		if (newJwtAccessToken != null ) {
			
			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, jwtService.generateAccessJwtCookieFromToken(newJwtAccessToken).toString())
					.build();
		}
		
		return ResponseEntity.badRequest().build();
		
		
	}
	
}
