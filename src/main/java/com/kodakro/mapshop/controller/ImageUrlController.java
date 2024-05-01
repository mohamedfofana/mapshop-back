package com.kodakro.mapshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.dto.ImageUrlDTO;
import com.kodakro.mapshop.service.ImageUrlService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wl/imageUrl")
@RequiredArgsConstructor
@Tag(name = "ImageUrl")
public class ImageUrlController {

	private final ImageUrlService imageUrlService;
	
	@GetMapping("/findAllByProduct/{id}")
	public List<ImageUrlDTO> findByProduct(@PathVariable Integer id){
		
		return imageUrlService.findByProduct(id);
	}
}
