package com.kodakro.mapshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends AbstractResourceException {

	private static final String message = "not found";

	public ResourceNotFoundException(String resourceClass) {
		super(resourceClass, message);
	}
}
