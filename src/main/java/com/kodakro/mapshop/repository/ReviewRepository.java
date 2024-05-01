package com.kodakro.mapshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodakro.mapshop.entity.Product;
import com.kodakro.mapshop.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	List<Review> findByProduct(Product product);
}
