package com.kodakro.mapshop.entity;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kodakro.mapshop.entity.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Entity
@Table(name="customer")
public class Customer implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstname; 
	
	private String lastname; 
	
	private Date birthdate; 
	
	private String email; 
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;
	
	
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonManagedReference
	private List<Token> tokens;
    
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonBackReference
	private List<Review> reviews;
	
    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> likedProducts;
    
    @ManyToMany(mappedBy = "flagged", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Review> flaggedReviews;
    
    @ManyToMany(mappedBy = "useful", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Review> usefulReviews;

    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	} 
	
}
