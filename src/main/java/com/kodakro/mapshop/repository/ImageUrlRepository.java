package com.kodakro.mapshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodakro.mapshop.entity.ImageUrl;
import com.kodakro.mapshop.entity.Product;


public interface ImageUrlRepository extends JpaRepository<ImageUrl, Integer>{
	List<ImageUrl> findByProduct(Product product);
}
