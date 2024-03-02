package com.kodakro.mapshop.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodakro.mapshop.domain.Customer;
import com.kodakro.mapshop.domain.Token;
import com.kodakro.mapshop.dto.AuthenticationRequest;
import com.kodakro.mapshop.dto.AuthenticationResponse;
import com.kodakro.mapshop.dto.TokenType;
import com.kodakro.mapshop.exception.ResourceAlreadyExistsException;
import com.kodakro.mapshop.exception.ResourceNotFoundException;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.TokenRepository;
import com.kodakro.mapshop.service.CustomerService;
import com.kodakro.mapshop.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import static com.kodakro.mapshop.security.helpers.JwtConstants.BEARER_TOKEN_PREFIX;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(Customer requestCustomer) {
		var customer = Customer.builder()
				.firstname(requestCustomer.getFirstname())
				.lastname(requestCustomer.getLastname())
				.age(requestCustomer.getAge())
				.email(requestCustomer.getEmail())
				.password(passwordEncoder.encode(requestCustomer.getPassword()))
				.role(requestCustomer.getRole())
				.build();
		
		var dbcustomer = customerRepository.findByEmail(requestCustomer.getEmail());
		if(dbcustomer.isPresent()) {
			throw new ResourceAlreadyExistsException(Customer.class.getName());
		}
		
		var savedCustomer = customerRepository.save(customer);
		var authenticationResponse = generateAuthenticationResponse(customer);
		
		saveCustomerToken(savedCustomer, authenticationResponse.getAccessToken());
		
		return authenticationResponse;
	}

	public AuthenticationResponse login(AuthenticationRequest request) {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		}catch (BadCredentialsException e) {
            throw new ResourceNotFoundException(Customer.class.getName());
        }
		var customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException(Customer.class.getName()));
		var authenticationResponse = generateAuthenticationResponse(customer);
		
		revokeAllCustomerTokens(customer);
		saveCustomerToken(customer, authenticationResponse.getAccessToken());
		
		return authenticationResponse;
	}

	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String customerEmail;
		
		if (authHeader == null ||!authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
			return;
		}
		refreshToken = authHeader.substring(7);
		customerEmail = jwtService.extractUsername(refreshToken);
		if (customerEmail != null) {
			var customer = this.customerRepository.findByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException(Customer.class.getName()));
			
			if (jwtService.isTokenValid(refreshToken, customer)) {
				var accessToken = jwtService.generateToken(customer);
				
				revokeAllCustomerTokens(customer);
				saveCustomerToken(customer, accessToken);
				
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	private AuthenticationResponse generateAuthenticationResponse(Customer customer) {
		var jwtToken = jwtService.generateToken(customer);
		var refreshToken = jwtService.generateRefreshToken(customer);
		
		
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	private void saveCustomerToken(Customer user, String jwtToken) {
		var token = Token.builder()
				.customer(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		
		tokenRepository.save(token);
	}

	private void revokeAllCustomerTokens(Customer user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		
		if (validUserTokens.isEmpty()) {
			return;
		}
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		
		tokenRepository.saveAll(validUserTokens);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
}
