package com.kodakro.mapshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
