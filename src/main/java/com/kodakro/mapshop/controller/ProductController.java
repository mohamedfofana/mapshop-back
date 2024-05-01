package com.kodakro.mapshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.dto.ProductDTO;
import com.kodakro.mapshop.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductController {

	private final ProductService productService;
	
	@GetMapping("/wl/product/findAllByInListIds/{listIds}")
	public List<ProductDTO> findAllByInListIds(@PathVariable List<Integer> listIds){
		return productService.findAllByInListIds(listIds);
	}
	
	@GetMapping("/wl/product/findAllByPage/{page}/{size}")
	public List<ProductDTO> findPage(@PathVariable Integer page, @PathVariable Integer size){
		return productService.findPage(page, size);
	}

	@GetMapping("/wl/product/findAllByCategoryAndPage/{page}/{size}/{categoryId}")
	public List<ProductDTO> findAllByCategoryAndPage(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer categoryId){
		return productService.findAllByCategoryAndPage(page, size, categoryId);
	}
	
	@GetMapping("/wl/product/findByTitleContainsIgnoreCase/{title}")
	public List<ProductDTO> findByTitleContainsIgnoreCase(@PathVariable String title){
		return productService.findByTitleContainsIgnoreCase(title);
	}

	@GetMapping("/wl/product/findAllByCategory/{categoryId}")
	public List<ProductDTO> findAllByCategory(@PathVariable Integer categoryId){
		return productService.findAllByCategory(categoryId);
	}
	
	@GetMapping("/wl/product/find/{id}")
	public ProductDTO findById(@PathVariable Integer id){
		return productService.findById(id);
	}
	
	@GetMapping("/wl/product/countAvailable")
	public long count(){
		return productService.countAvailable();
	}
	
	@PostMapping("/product/likeProduct/{productId}/{customerEmail}/{value}")
	public void likeProduct(@PathVariable() Integer productId, @PathVariable() String customerEmail, @PathVariable() Boolean value) {
		productService.likeProduct(productId, customerEmail, value);
	}
	
}
