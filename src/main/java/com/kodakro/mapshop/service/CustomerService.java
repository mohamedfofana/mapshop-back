package com.kodakro.mapshop.service;

import java.util.List;

import com.kodakro.mapshop.entity.dto.CustomerDTO;

public interface CustomerService {
	List<CustomerDTO> findAll();
	CustomerDTO findByEmail(String email);
}
