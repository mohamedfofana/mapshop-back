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
public class ProductDTO {

	private Integer id;
	
	private String title; 
	
	private String description; 
	
	private String brand;
	
	private Integer stock; 
	
	private float price; 
	
	private float rating;
	
	private float discountPercentage;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private CategoryDTO category;
	
	private List<ImageUrlDTO> imageUrls;

	private List<ReviewDTO> reviews;
	
	private List<CustomerDTO> likes;
	
}
