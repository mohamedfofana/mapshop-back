package com.kodakro.mapshop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.dto.CustomerDTO;
import com.kodakro.mapshop.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer")
public class CustomerController {
	
	private final CustomerService customerService;

	  @PostMapping("/auth/register")
	  public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody CustomerDTO requestCustomer) {
	    return ResponseEntity.ok(customerService.register(requestCustomer));
	  }
	  
	  @PostMapping("/auth/login")
	  public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationRequestDTO requestCustomer) {
	    return ResponseEntity.ok(customerService.login(requestCustomer));
	  }
	  
	  @GetMapping("/all")
	  public ResponseEntity<List<CustomerDTO>> findAll(){
		  return ResponseEntity.ok(customerService.findAll());
	  }
	  
	  @PostMapping("/auth/refresh-token")
	  public void refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response
	  ) throws IOException {
		  customerService.refreshToken(request, response);
	  }

}
