package com.kodakro.mapshop.service;

import java.io.IOException;
import java.util.List;

import com.kodakro.mapshop.domain.Customer;
import com.kodakro.mapshop.dto.AuthenticationRequest;
import com.kodakro.mapshop.dto.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {
	AuthenticationResponse register(Customer request);
	AuthenticationResponse login(AuthenticationRequest request);
	List<Customer> findAll();
	void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
