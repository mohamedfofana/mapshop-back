package com.kodakro.mapshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodakro.mapshop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	List<Category> findByTitle(String title);
}
