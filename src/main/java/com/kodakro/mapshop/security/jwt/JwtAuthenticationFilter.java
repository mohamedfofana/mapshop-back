package com.kodakro.mapshop.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kodakro.mapshop.repository.TokenRepository;
import com.kodakro.mapshop.service.JwtService;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	private final JwtService jwtService;
	private final TokenRepository tokenRepository;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@Nonnull HttpServletRequest request, 
			@Nonnull HttpServletResponse response, 
			@Nonnull FilterChain filterChain)
					throws ServletException, IOException {
		final String customerEmail;
		String jwt = null;
		
		if(isWhiteListedUrl(request)) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		jwt = jwtService.getAccessTokenFromCookie(request.getCookies());
		
		if(jwt == null) {
			filterChain.doFilter(request, response);
			
			return;
		}
		
		customerEmail = jwtService.extractUsername(jwt);
	    if (customerEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	      UserDetails userDetails = this.userDetailsService.loadUserByUsername(customerEmail);
	      var isTokenEnabled = tokenRepository.findByToken(jwt)
	    		  .map(t -> !t.isExpired() && !t.isRevoked())
	    		  .orElse(false);
	      
	      if (jwtService.isTokenValid(jwt, userDetails) && isTokenEnabled) {
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	            userDetails,
	            null,
	            userDetails.getAuthorities()
	        );
	        authToken.setDetails(
	            new WebAuthenticationDetailsSource().buildDetails(request)
	        );
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	      }
	    }
	    filterChain.doFilter(request, response);
		

	}

	private boolean isWhiteListedUrl(HttpServletRequest request) {
		return request.getRequestURL().toString().contains("/api/auth/") 
				|| request.getRequestURL().toString().contains("/api/wl/");
	}

}
