package com.kodakro.mapshop.entity.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private Integer id;
	
	private String title; 
	
	private Integer rate; 
	
	private String comment;
	
	private Date createdAt;
	
	private CustomerDTO customer;
	
	private List<CustomerDTO> flagged;
	
	private List<CustomerDTO> useful;
	
}
