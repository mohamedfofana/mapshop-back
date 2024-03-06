package com.kodakro.mapshop.entity.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public final class GlobalEntityMapper {
	
	
	public static  <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {
	    return source
			      .stream()
			      .map(element -> modelMapper.map(element, targetClass))
			      .collect(Collectors.toList());
	}
}
