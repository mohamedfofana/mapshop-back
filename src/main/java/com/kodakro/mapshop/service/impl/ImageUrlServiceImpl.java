package com.kodakro.mapshop.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.dto.ImageUrlDTO;
import com.kodakro.mapshop.entity.mapper.GlobalEntityMapper;
import com.kodakro.mapshop.repository.ImageUrlRepository;
import com.kodakro.mapshop.repository.ProductRepository;
import com.kodakro.mapshop.service.ImageUrlService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUrlServiceImpl implements ImageUrlService {

	private final ImageUrlRepository imageUrlRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public List<ImageUrlDTO> findByProduct(Integer id) {
		var product  = productRepository.findById(id).get();
		var imageUrls = imageUrlRepository.findByProduct(product);
		
		return GlobalEntityMapper.mapList( imageUrls, ImageUrlDTO.class, modelMapper);
	}

}
