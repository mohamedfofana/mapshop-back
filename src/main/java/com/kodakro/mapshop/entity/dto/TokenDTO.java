package com.kodakro.mapshop.entity.dto;

import com.kodakro.mapshop.entity.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
	public Integer id;

	public String token;

	public TokenType tokenType = TokenType.BEARER;

	public boolean revoked;

	public boolean expired;
}
