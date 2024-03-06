package com.kodakro.mapshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodakro.mapshop.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	Optional<Customer> findByEmail(String email);

}
