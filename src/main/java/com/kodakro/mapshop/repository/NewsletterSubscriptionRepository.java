package com.kodakro.mapshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodakro.mapshop.entity.NewsletterSubscription;

public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscription, Integer>{
	List<NewsletterSubscription> findByEmail(String email);
}
