package com.kodakro.mapshop.dto;

import com.kodakro.mapshop.domain.enums.TokenType;

import lombok.Data;

@Data
public class TokenDTO {
	public Integer id;

	public String token;

	public TokenType tokenType = TokenType.BEARER;

	public boolean revoked;

	public boolean expired;
}
