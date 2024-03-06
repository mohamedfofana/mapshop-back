package com.kodakro.mapshop.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponseTypeDTO<T> {
	private String status;
	private T entity;
	private String message;
	
	
	public CustomResponseTypeDTO(String message) {
		this.message = message;
	}


	public CustomResponseTypeDTO(String status, T entity, String message) {
		super();
		this.status = status;
		this.entity = entity;
		this.message = message;
	}
}
