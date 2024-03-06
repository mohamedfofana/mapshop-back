package com.kodakro.mapshop.service;

import java.io.IOException;
import java.util.List;

import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.entity.dto.CustomerDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {
	AuthenticationResponseDTO register(CustomerDTO request);
	AuthenticationResponseDTO login(AuthenticationRequestDTO request);
	List<CustomerDTO> findAll();
	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
