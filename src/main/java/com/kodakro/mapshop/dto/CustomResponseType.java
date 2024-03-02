package com.kodakro.mapshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseType<T> {
	private String status;
	private T entity;
	private String message;
	
	
	public CustomResponseType(String message) {
		this.message = message;
	}


	public CustomResponseType(String status, T entity, String message) {
		super();
		this.status = status;
		this.entity = entity;
		this.message = message;
	}
}
