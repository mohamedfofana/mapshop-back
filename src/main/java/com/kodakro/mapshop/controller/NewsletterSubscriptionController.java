package com.kodakro.mapshop.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.mapshop.entity.NewsletterSubscription;
import com.kodakro.mapshop.entity.dto.NewsletterSubscriptionDTO;
import com.kodakro.mapshop.service.NewsletterSubscriptionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wl/newsletterSubscription")
@RequiredArgsConstructor
@Tag(name = "Newsletter_subscription")
public class NewsletterSubscriptionController {

	private final NewsletterSubscriptionService newsletterSubscriptionService;
	
	@PostMapping("/subscribe")
	public NewsletterSubscriptionDTO subscribe(@Valid @RequestBody NewsletterSubscription newsletterSubscription){
		
		return newsletterSubscriptionService.subscribe(newsletterSubscription);
	}
}
