package com.kodakro.mapshop.service.impl;

import java.sql.Timestamp;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kodakro.mapshop.entity.NewsletterSubscription;
import com.kodakro.mapshop.entity.dto.NewsletterSubscriptionDTO;
import com.kodakro.mapshop.repository.NewsletterSubscriptionRepository;
import com.kodakro.mapshop.service.NewsletterSubscriptionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsletterSubscriptionServiceImpl implements NewsletterSubscriptionService{

	private final NewsletterSubscriptionRepository newsletterSubscriptionRepository;
	private final ModelMapper modelMapper;
	@Override
	public NewsletterSubscriptionDTO subscribe(NewsletterSubscription subscription) {
		
		var sub = newsletterSubscriptionRepository.findByEmail(subscription.getEmail());
		var dbSub = subscription;
		
		if(!sub.isEmpty()) {
			dbSub = sub.get(0);
			dbSub.setRenewed_at(Timestamp.from(Instant.now()));;
		}
		
		var savedSub = newsletterSubscriptionRepository.save(dbSub);
		
		return modelMapper.map(savedSub, NewsletterSubscriptionDTO.class);
	}

}
