package com.kodakro.mapshop.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kodakro.mapshop.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration{
	private static final String[] WHITE_LIST_URL = {"/api/customer/auth/**",
										            "/v3/api-docs",
										            "/v3/api-docs/**",
										            "/swagger-resources",
										            "/swagger-resources/**",
										            "/configuration/ui",
										            "/configuration/security",
										            "/swagger-ui/**",
										            "/webjars/**",
										            "/swagger-ui.html"};
	private static final String[] WHITE_LIST_HOST = {"http://localhost:4200"};
	private static final String[] WHITE_LIST_HTTP_METHOD = {"HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"};
	private static final String[] WHITE_LIST_HTTP_HEADERS = {"X-Requested-With", "Origin", "Accept", 
															 "Authorization", "Cache-Control", "Content-Type"};
	
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AccessDeniedHandler accessDeniedHandler;
    private final LogoutHandler logoutHandler;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
        http
        	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
        	.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
            	req.requestMatchers(WHITE_LIST_URL).permitAll()
                   .anyRequest()
                   .authenticated()
                   )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler))
            .logout(logout -> logout.logoutUrl("/api/customer/auth/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    )
        ;

        return http.build();
    }
    
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(WHITE_LIST_HOST));
        configuration.setAllowedMethods(Arrays.asList(WHITE_LIST_HTTP_METHOD));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(WHITE_LIST_HTTP_HEADERS));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
	
}
