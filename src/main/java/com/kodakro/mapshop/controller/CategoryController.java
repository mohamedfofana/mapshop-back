package com.kodakro.mapshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.dto.CategoryDTO;
import com.kodakro.mapshop.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wl/category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

	private final CategoryService categoryService;
	
	@GetMapping("/findAll")
	public List<CategoryDTO> findAll(){
		return categoryService.findAll();
	}
}
