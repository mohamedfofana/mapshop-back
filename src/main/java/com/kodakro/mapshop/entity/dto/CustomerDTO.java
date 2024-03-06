package com.kodakro.mapshop.entity.dto;

import java.util.Date;

import com.kodakro.mapshop.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
