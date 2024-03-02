package com.kodakro.mapshop.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "kodakro",
						email = "contact@mapshop.com",
						url = "https://mapshop.com"
						),
				description = "OpenApi documentation for Mapshop backend",
				title = "OpenApi specification",
				version = "1.0",
				license = @License(
						name = "Licence name",
						url = "https://my-license-url.com"
						),
				termsOfService = "My Terms of service"
				),
		servers = {
				@Server(
						description = "Dev env",
						url = "http://localhost:8181"
						),
				@Server(
						description = "Prod env",
						url = "https://mapshop.com"
						)
		},
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
						)
		}
		)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
@Configuration
public class OpenApiconfig {

}
