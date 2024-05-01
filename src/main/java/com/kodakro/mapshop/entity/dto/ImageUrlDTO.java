package com.kodakro.mapshop.entity.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrlDTO {
	private Integer id;
	
	private String url; 
	
	private String type;  

	private Date createdAt;
}
