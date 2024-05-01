package com.kodakro.mapshop.service;

import java.util.List;

import com.kodakro.mapshop.entity.dto.CategoryDTO;

public interface CategoryService {
	List<CategoryDTO> findAll();
}
