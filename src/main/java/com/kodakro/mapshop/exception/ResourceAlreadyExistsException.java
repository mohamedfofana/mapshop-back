package com.kodakro.mapshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public final class ResourceAlreadyExistsException extends AbstractResourceException {

	private static final String message = "already exists";

	public ResourceAlreadyExistsException(String resourceClass) {
		super(resourceClass, message);
	}
}
