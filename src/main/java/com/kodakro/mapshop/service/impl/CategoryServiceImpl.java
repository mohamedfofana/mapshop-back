package com.kodakro.mapshop.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.dto.CategoryDTO;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.repository.CategoryRepository;
import com.kodakro.mapshop.service.CategoryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository customerRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<CategoryDTO> findAll() {
		var customerLists = customerRepository.findAll();

		return GlobalEntityMapper.mapList(customerLists, CategoryDTO.class, modelMapper);
	}
	
}
