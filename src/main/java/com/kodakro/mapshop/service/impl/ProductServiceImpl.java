package com.kodakro.mapshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.dto.ProductDTO;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.repository.CategoryRepository;
import com.kodakro.mapshop.repository.CustomerRepository;
import com.kodakro.mapshop.repository.ProductRepository;
import com.kodakro.mapshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	private final int max_same_category = 20;
	private final Sort sortBylastCreated = Sort.by("createdAt").descending();
	
	@Override
	public void likeProduct(Integer productId, String customerEmail, Boolean value) {
		var product = productRepository.findById(productId).get();
		var customer = customerRepository.findByEmail(customerEmail);
		var newLikes = product.getLikes().stream().filter(c -> !c.getEmail().equals(customerEmail)).collect(Collectors.toList());
		if(value) {
			newLikes.add(customer.get());
		}
		product.setLikes(newLikes);
		
		productRepository.save(product);
	}

	@Override
	public List<ProductDTO> findPage(int page, int size) {
		var productPage = productRepository.findAll(PageRequest.of(page, size, sortBylastCreated));
		
		return  GlobalEntityMapper.mapList( productPage.getContent(), ProductDTO.class, modelMapper);
	}


	@Override
	public List<ProductDTO> findAllByCategoryAndPage(int page, int size, Integer categoryId) {
		var productPage = productRepository.findAllByCategoryAndPage(categoryId, PageRequest.of(page, size, sortBylastCreated));
		
		return  GlobalEntityMapper.mapList( productPage.getContent(), ProductDTO.class, modelMapper);
	}
	
	@Override
	public List<ProductDTO> findAllByInListIds(List<Integer> listIds) {
		var products = productRepository.findAllByInListIds(listIds);
		
		return  GlobalEntityMapper.mapList( products, ProductDTO.class, modelMapper);
	}
	
	@Override
	public List<ProductDTO> findByTitle(String title) {
		var products = productRepository.findByTitle(title);
		
		return  GlobalEntityMapper.mapList( products, ProductDTO.class, modelMapper);
	}

	@Override
	public List<ProductDTO> findByTitleContainsIgnoreCase(String title) {
		var products = productRepository.findByTitleContainsIgnoreCase(title);
		
		return  GlobalEntityMapper.mapList( products, ProductDTO.class, modelMapper);
	}

	@Override
	public List<ProductDTO> findAllByCategory(int categoryId) {
		var category = categoryRepository.findById(categoryId);
		if(category.isEmpty()) {
			return List.of();
		}
		var products = productRepository.findByCategory(category.get());
		
		return  GlobalEntityMapper.mapList( products.stream().limit(max_same_category).toList(), ProductDTO.class, modelMapper);
	}
	
	@Override
	public long countAvailable() {
		var productPage = productRepository.findAll(PageRequest.of(0, 1));
		return productPage.getTotalElements();
	}

	@Override
	public ProductDTO findById(int id) {
		var product = productRepository.findById(id);
		
		return modelMapper.map(product, ProductDTO.class);
	}


}
