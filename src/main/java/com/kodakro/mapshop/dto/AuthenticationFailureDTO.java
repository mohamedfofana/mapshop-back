package com.kodakro.mapshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationFailureDTO {
	private static final String message = "Login or password incorect.";
}
