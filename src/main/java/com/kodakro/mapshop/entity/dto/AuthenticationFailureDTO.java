package com.kodakro.mapshop.entity.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationFailureDTO {
	private static final String message = "Login or password incorect.";
}
