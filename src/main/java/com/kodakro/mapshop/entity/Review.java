package com.kodakro.mapshop.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title; 
	
	private Integer rate; 
	
	private String comment;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@JsonManagedReference
	public Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	@JsonBackReference
	public Product product;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "review_flagged", 
      joinColumns = @JoinColumn(name = "review_id"), 
      inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@JsonManagedReference 
	private List<Customer> flagged;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
      name = "review_useful", 
      joinColumns = @JoinColumn(name = "review_id"), 
      inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@JsonManagedReference 
	private List<Customer> useful;
}
