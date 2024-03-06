package com.kodakro.mapshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.Token;
import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.entity.dto.CustomerDTO;
import com.kodakro.mapshop.entity.enums.Role;
import com.kodakro.mapshop.entity.enums.TokenType;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.exception.ResourceAlreadyExistsException;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.TokenRepository;
import com.kodakro.mapshop.service.JwtService;
import com.kodakro.mapshop.service.impl.CustomerServiceImpl;
import static org.mockito.BDDMockito.given;
public class CustomerServiceTest {

	@InjectMocks 
	private CustomerServiceImpl customerService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private TokenRepository tokenRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtService jwtService;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private GlobalEntityMapper globalEntityMapper;

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
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final CustomerDTO customerDTO2 = CustomerDTO.builder()
				.id(2)
				.firstname("userfirstname2")
				.lastname("userlastname2")
				.age(29)
				.createdAt(new Date())
				.email("test2@mapshop.com")
				.password("pass2")
				.role(Role.USER)
				.build();
		final Customer customer1 = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final Customer customer2 = Customer.builder()
				.id(2)
				.firstname("userfirstname2")
				.lastname("userlastname2")
				.age(29)
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

	@Test
	void registerTest_Exception() {
		// Given
		final CustomerDTO customerDTO1 = CustomerDTO.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final Customer customer1 = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();

		// Mock returns
		when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(Optional.of(customer1));

		// When 

		// Then
		assertThrows(ResourceAlreadyExistsException.class, () -> customerService.register(customerDTO1));
	}

	@Test
	void registerTest() {
		// Given
		final CustomerDTO customerDTO1 = CustomerDTO.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final Customer customer1 = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.age(19)
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
				.role(Role.USER)
				.build();
		final AuthenticationResponseDTO authenticationResponseDTO = AuthenticationResponseDTO.builder()
				.accessToken("token1")
				.refreshToken("refreshToken1")
				.build();
		var token = Token.builder()
				.customer(customer1)
				.token(authenticationResponseDTO.getAccessToken())
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();


		// Mock returns
		when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(Optional.empty());
		when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
		when(tokenRepository.save(any(Token.class))).thenReturn(token);

		// When 
		final AuthenticationResponseDTO expectedAuthenticationResponseDTO = customerService.register(customerDTO1);

		// Then
		assertNotNull(expectedAuthenticationResponseDTO);
		assertEquals(customer1.getId(), customerDTO1.getId());

	}

	@Test
	void loginTest() {
		// Given
				final CustomerDTO customerDTO1 = CustomerDTO.builder()
						.id(1)
						.firstname("userfirstname")
						.lastname("userlastname")
						.age(19)
						.createdAt(new Date())
						.email("test@mapshop.com")
						.password("pass")
						.role(Role.USER)
						.build();
				final Customer customer1 = Customer.builder()
						.id(1)
						.firstname("userfirstname")
						.lastname("userlastname")
						.age(19)
						.createdAt(new Date())
						.email("test@mapshop.com")
						.password("pass")
						.role(Role.USER)
						.build();
				final AuthenticationResponseDTO authenticationResponseDTO = AuthenticationResponseDTO.builder()
						.accessToken("token1")
						.refreshToken("refreshToken1")
						.build();
				var token = Token.builder()
						.customer(customer1)
						.token(authenticationResponseDTO.getAccessToken())
						.tokenType(TokenType.BEARER)
						.expired(false)
						.revoked(false)
						.build();
				var  authenticationRequestDTO = AuthenticationRequestDTO.builder()
													.email(customer1.getEmail())
													.password("pass")
													.build();

				// Mock returns
				when(customerRepository.findByEmail(customer1.getEmail())).thenReturn(Optional.of(customer1));
				when(tokenRepository.findAllValidTokenByUser(any(Integer.class))).thenReturn(new ArrayList<>());
				when(tokenRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
				when(tokenRepository.save(any(Token.class))).thenReturn(token);
//				when(customerService.login(authenticationRequestDTO)).thenReturn(authenticationResponseDTO);

				// When 
				final AuthenticationResponseDTO expectedAuthenticationResponseDTO = customerService.login(authenticationRequestDTO);

				// Then
				assertNotNull(expectedAuthenticationResponseDTO);
				assertEquals(customer1.getId(), customerDTO1.getId());

				verify(customerRepository, times(1)).findByEmail(customer1.getEmail());
	}

}