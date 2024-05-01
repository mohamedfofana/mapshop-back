package com.kodakro.mapshop.service.impl;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.Customer;
import com.kodakro.mapshop.entity.Token;
import com.kodakro.mapshop.entity.dto.AuthenticationRequestDTO;
import com.kodakro.mapshop.entity.dto.AuthenticationResponseDTO;
import com.kodakro.mapshop.entity.enums.TokenType;
import com.kodakro.mapshop.exception.ResourceAlreadyExistsException;
import com.kodakro.mapshop.exception.ResourceNotFoundException;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.TokenRepository;
import com.kodakro.mapshop.service.AuthService;
import com.kodakro.mapshop.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final CustomerRepository customerRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponseDTO register(Customer requestCustomer) {
		var customer = Customer.builder()
				.firstname(requestCustomer.getFirstname())
				.lastname(requestCustomer.getLastname())
				.birthdate(requestCustomer.getBirthdate())
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

	public AuthenticationResponseDTO login(AuthenticationRequestDTO request) {
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

	public String refreshToken(
			HttpServletRequest request
			) throws IOException {
		final String refreshToken;
		final String customerEmail;

		refreshToken = jwtService.getRefreshTokenFromCookie(request.getCookies());
		customerEmail = jwtService.extractUsername(refreshToken);
		if (customerEmail != null) {
			var customer = this.customerRepository.findByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException(Customer.class.getName()));

			if (jwtService.isTokenValid(refreshToken, customer)) {
				final String accessToken = jwtService.generateToken(customer);

				revokeAllCustomerTokens(customer);
				saveCustomerToken(customer, accessToken);

				return accessToken;
			}
		}
		
		return null;
	}

	private AuthenticationResponseDTO generateAuthenticationResponse(Customer customer) {
		var jwtToken = jwtService.generateToken(customer);
		var refreshToken = jwtService.generateRefreshToken(customer);


		return AuthenticationResponseDTO.builder()
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
}
