package com.kodakro.mapshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.dto.CustomerDTO;
import com.kodakro.mapshop.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer")
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("/all")
	public ResponseEntity<List<CustomerDTO>> findAll(){
		return ResponseEntity.ok(customerService.findAll());
	}
	
	
	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<CustomerDTO> findByEmail(@PathVariable String email){
		return ResponseEntity.ok(customerService.findByEmail(email));
	}

}
