package com.kodakro.mapshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.Token;
import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.entity.enums.Role;
import com.kodakro.mapshop.entity.enums.TokenType;
import com.kodakro.mapshop.exception.ResourceAlreadyExistsException;
import com.kodakro.mapshop.helpers.DateTimeHelper;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.TokenRepository;
import com.kodakro.mapshop.service.JwtService;
public class AuthServiceTest {

	@InjectMocks 
	private AuthServiceImpl authService;
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

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void registerTest_Exception() {
		// Given
		final Customer customerReq1 = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.birthdate(DateTimeHelper.stringToUtilDate("19920305"))
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
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

		// Mock returns
		when(customerRepository.findByEmail(customerReq1.getEmail())).thenReturn(Optional.of(customer1));

		// When 

		// Then
		assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(customer1));
	}

	@Test
	void registerTest() {
		// Given
		final Customer customer = Customer.builder()
				.id(1)
				.firstname("userfirstname")
				.lastname("userlastname")
				.birthdate(DateTimeHelper.stringToUtilDate("19920305"))
				.createdAt(new Date())
				.email("test@mapshop.com")
				.password("pass")
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
		final AuthenticationResponseDTO expectedAuthenticationResponseDTO = authService.register(customer);

		// Then
		assertNotNull(expectedAuthenticationResponseDTO);
		assertEquals(customer1.getId(), customer.getId());

	}

	@Test
	void loginTest() {
		// Given
				final Customer customerReq = Customer.builder()
						.id(1)
						.firstname("userfirstname")
						.lastname("userlastname")
						.birthdate(DateTimeHelper.stringToUtilDate("19920305"))
						.createdAt(new Date())
						.email("test@mapshop.com")
						.password("pass")
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
				final AuthenticationResponseDTO expectedAuthenticationResponseDTO = authService.login(authenticationRequestDTO);

				// Then
				assertNotNull(expectedAuthenticationResponseDTO);
				assertEquals(customer1.getId(), customerReq.getId());

				verify(customerRepository, times(1)).findByEmail(customer1.getEmail());
	}

}