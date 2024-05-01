package com.kodakro.mapshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.dto.ReviewDTO;
import com.kodakro.mapshop.entity.dto.ReviewRequestDTO;
import com.kodakro.mapshop.service.ReviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Review")
public class ReviewController {

	private final ReviewService reviewService;
	
	@GetMapping("/wl/review/findByProduct/{id}")
	public List<ReviewDTO> findByProduct(@PathVariable Integer id){
		
		return reviewService.findByProduct(id);
	}
	
	@PostMapping("/review/save")
	public void save(@RequestBody() ReviewRequestDTO review) {
		
		reviewService.save(review);
	}
	
	@PostMapping("/review/flagReview/{reviewId}/{customerEmail}/{value}")
	public void flagReview(@PathVariable() Integer reviewId, @PathVariable() String customerEmail, @PathVariable() Boolean value) {
		reviewService.flagReview(reviewId, customerEmail , value);
	}
	
	@PostMapping("/review/markUsefulReview/{reviewId}/{customerEmail}/{value}")
	public void markUsefulReview(@PathVariable() Integer reviewId, @PathVariable() String customerEmail, @PathVariable() Boolean value) {
		reviewService.markUsefulReview(reviewId, customerEmail, value);
	}
	
	
}
