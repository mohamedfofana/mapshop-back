package com.kodakro.mapshop.service;

import java.util.List;

import com.kodakro.mapshop.entity.dto.ReviewDTO;

public interface ReviewService {

	List<ReviewDTO> findByProduct(Integer id);
	void save(ReviewDTO review);
	void flagReview(Integer reviewId, String customerEmail, Boolean value);
	void markUsefulReview(Integer reviewId, String customerEmail, Boolean value);
	
}
