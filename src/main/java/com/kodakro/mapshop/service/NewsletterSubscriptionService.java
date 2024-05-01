package com.kodakro.mapshop.service;

import com.kodakro.mapshop.entity.NewsletterSubscription;
import com.kodakro.mapshop.entity.dto.NewsletterSubscriptionDTO;

public interface NewsletterSubscriptionService {
	
	NewsletterSubscriptionDTO subscribe(NewsletterSubscription subscription);
}
