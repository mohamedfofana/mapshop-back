package com.kodakro.mapshop.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
@Table(name="product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title; 
	
	private String description; 
	
	private String brand;
	
	private Integer stock; 
	
	private float price; 
	
	private float rating;
	
	private float discountPercentage;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updatedAt;
	
    @ManyToMany
    @JoinTable(
      name = "product_like", 
      joinColumns = @JoinColumn(name = "product_id"), 
      inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@JsonManagedReference 
	private List<Customer> likes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonManagedReference
	private Category category;
	
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
	private List<ImageUrl> imageUrls;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
	private List<Review> reviews;
}
