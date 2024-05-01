package com.kodakro.mapshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kodakro.mapshop.entity.Category;
import com.kodakro.mapshop.entity.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, CrudRepository<Product, Integer>{
	List<Product> findByCategory(Category category);
	List<Product> findByBrand(String brand);
	List<Product> findByTitle(String title);
	List<Product> findByTitleContainsIgnoreCase(String title);
	Optional<Product> findById(Integer id);
	@Query("Select p from product p where p.id in :listIds")
	List<Product>  findAllByInListIds(List<Integer> listIds);
	
	@Query("Select p from product p inner join p.category c where c.id = :categoryId")
	Page<Product> findAllByCategoryAndPage(Integer categoryId, Pageable pageable);
}
