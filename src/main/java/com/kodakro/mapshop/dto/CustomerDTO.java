package com.kodakro.mapshop.dto;

import java.util.Date;

import com.kodakro.mapshop.domain.enums.Role;

import lombok.Data;

@Data
public class CustomerDTO {
	private Integer id;
	
	private String firstname; 
	
	private String lastname; 
	
	private Integer age; 
	
	private String email; 
	
	private String password;
	
	private Role role;
	
	private Date createdAt;
}
