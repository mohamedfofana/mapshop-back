package com.kodakro.mapshop.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.dto.CustomerDTO;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<CustomerDTO> findAll() {
		var customerLists = customerRepository.findAll();

		return GlobalEntityMapper.mapList(customerLists, CustomerDTO.class, modelMapper);
	}
	
	@Override
	public CustomerDTO findByEmail(String email) {
		var customer = customerRepository.findByEmail(email);
		
		return modelMapper.map(customer, CustomerDTO.class);
	}
	
}
