package com.kodakro.mapshop.service;

import java.util.List;

import com.kodakro.mapshop.entity.dto.ProductDTO;

public interface ProductService {
	List<ProductDTO> findPage(int page, int size);
	List<ProductDTO> findByTitle(String title);
	List<ProductDTO> findByTitleContainsIgnoreCase(String title);
	List<ProductDTO> findAllByCategory(int categoryId);
	ProductDTO findById(int id);
	long countAvailable();
	List<ProductDTO> findAllByInListIds(List<Integer> listIds);
	List<ProductDTO> findAllByCategoryAndPage(int page, int size, Integer categoryId);
	void likeProduct(Integer productId, String customerEmail, Boolean value);
}
