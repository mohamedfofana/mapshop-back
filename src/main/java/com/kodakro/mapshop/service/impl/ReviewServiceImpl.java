
package com.kodakro.mapshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.Review;
import com.kodakro.mapshop.entity.dto.ReviewDTO;
import com.kodakro.mapshop.entity.dto.ReviewRequestDTO;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.ProductRepository;
import com.kodakro.mapshop.repository.ReviewRepository;
import com.kodakro.mapshop.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository; 
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public List<ReviewDTO> findByProduct(Integer id) {
		var product = productRepository.findById(id).get();
		var reviews = reviewRepository.findByProduct(product);
		var sortedReviews = reviews.stream()
								   .sorted((review1, review2) -> review2.getCreatedAt().compareTo(review1.getCreatedAt()))
								   .collect(Collectors.toList());
		
		return  GlobalEntityMapper.mapList( sortedReviews, ReviewDTO.class, modelMapper);
	}

	@Override
	public void save(ReviewDTO review) {
		var customer = customerRepository.findByEmail(review.getCustomer().getEmail());
		var product = productRepository.findById(((ReviewRequestDTO) review).getProduct().getId());
		var reviewToSave = Review.builder()
								 .comment(review.getComment())
								 .rate(review.getRate())
								 .title(review.getTitle())
								 .customer(customer.get())
								 .product(product.get())
								 .build();
		reviewRepository.save(reviewToSave);
		
	}

	@Override
	public void flagReview(Integer reviewId, String customerEmail, Boolean value) {
		var review = reviewRepository.findById(reviewId).get();
		var customer = customerRepository.findByEmail(customerEmail);
		var flagged = review.getFlagged().stream().filter(c -> !c.getEmail().equals(customerEmail)).collect(Collectors.toList());
		if(value) {
			flagged.add(customer.get());
		}
		review.setFlagged(flagged);
		
		reviewRepository.save(review);
	}

	@Override
	public void markUsefulReview(Integer reviewId, String customerEmail, Boolean value) {
		var review = reviewRepository.findById(reviewId).get();
		var customer = customerRepository.findByEmail(customerEmail);
		var useful = review.getUseful().stream().filter(c -> !c.getEmail().equals(customerEmail)).collect(Collectors.toList());
		if(value) {
			useful.add(customer.get());
		}
		review.setUseful(useful);
		
		reviewRepository.save(review);
		
	}
	
}
