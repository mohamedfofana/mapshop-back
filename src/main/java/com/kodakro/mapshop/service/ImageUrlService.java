package com.kodakro.mapshop.service;

import java.util.List;

import com.kodakro.mapshop.entity.dto.ImageUrlDTO;

public interface ImageUrlService {
	List<ImageUrlDTO> findByProduct(Integer id);
}
