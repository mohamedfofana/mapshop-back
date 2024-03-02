package com.kodakro.mapshop.exception;

import lombok.Getter;

@Getter
public sealed abstract class AbstractResourceException extends RuntimeException permits ResourceNotFoundException, ResourceAlreadyExistsException {

    public AbstractResourceException(String resourceClass, String message) {
        super(String.format("%s %s.", getSimpleName(resourceClass), message));
    }
    
    private static String getSimpleName(String resourceClass) {
    	final String[] packages = resourceClass.split("\\.");
    	
    	return packages[packages.length-1];
    }
}

;