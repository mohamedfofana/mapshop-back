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
public class NewsletterSubscriptionDTO {
	
	private String email; 
	
	private Date createdAt;
	
	private Date renewed_at;  
}
