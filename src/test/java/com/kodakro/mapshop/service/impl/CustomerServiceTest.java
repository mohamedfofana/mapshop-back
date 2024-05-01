package com.kodakro.mapshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.dto.CustomerDTO;
import com.kodakro.mapshop.entity.enums.Role;
import com.kodakro.mapshop.helpers.DateTimeHelper;
import com.kodakro.mapshop.repository.CustomerRepository;
public class CustomerServiceTest {

	@InjectMocks 
	private CustomerServiceImpl customerService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void findAllTest() {
		// Given
		List<CustomerDTO> customersDTOLists = new ArrayList<>();
		final CustomerDTO customerDTO1 = CustomerDTO.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.birthdate(DateTimeHelper.stringToUtilDate("19920305"))
				.createdAt(new Date())
				.email("test@mapshop.com")
				.role(Role.USER)
				.build();
		final CustomerDTO customerDTO2 = CustomerDTO.builder()
				.id(2)
				.firstname("userfirstname2")
				.lastname("userlastname2")
				.birthdate(DateTimeHelper.stringToUtilDate("19950305"))
				.createdAt(new Date())
				.email("test2@mapshop.com")
				.role(Role.USER)
				.build();
		final Customer customer1 = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.birthdate(DateTimeHelper.stringToUtilDate("19920305"))
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final Customer customer2 = Customer.builder()
				.id(2)
				.firstname("userfirstname2")
				.lastname("userlastname2")
				.birthdate(DateTimeHelper.stringToUtilDate("19950305"))
				.createdAt(new Date())
				.email("test2@mapshop.com")
				.password("pass2")
				.role(Role.USER)
				.build();

		customersDTOLists.add(customerDTO1);
		customersDTOLists.add(customerDTO2);
		List<Customer> customersLists = new ArrayList<>();
		customersLists.add(customer1);
		customersLists.add(customer2);

		// Mock returns
		when(customerRepository.findAll()).thenReturn(customersLists);
		when(modelMapper.map(customer1, CustomerDTO.class)).thenReturn(customerDTO1);
		when(modelMapper.map(customer2, CustomerDTO.class)).thenReturn(customerDTO2);

		// When 
		List<CustomerDTO> actualCustomers = customerService.findAll();

		// Then
		assertNotNull(actualCustomers);
		assertEquals(2, actualCustomers.size());

		verify(customerRepository, times(1)).findAll();
	}

}