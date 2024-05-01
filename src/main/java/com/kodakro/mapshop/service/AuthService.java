package com.kodakro.mapshop.service;

import java.io.IOException;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
	AuthenticationResponseDTO register(Customer request);
	AuthenticationResponseDTO login(AuthenticationRequestDTO request);
	String refreshToken(HttpServletRequest request) throws IOException;
}
