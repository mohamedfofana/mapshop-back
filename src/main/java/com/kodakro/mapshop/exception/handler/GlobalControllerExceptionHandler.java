package com.kodakro.mapshop.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kodakro.mapshop.exception.AbstractResourceException;
import com.kodakro.mapshop.exception.ResourceAlreadyExistsException;
import com.kodakro.mapshop.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


	@ExceptionHandler(AbstractResourceException.class)
	public ResponseEntity<String> handleResourceException(final AbstractResourceException ex) {
		log.warn(ex.getMessage());

		if (ex instanceof ResourceNotFoundException rex) {
			return new ResponseEntity<String>(rex.getMessage(), HttpStatus.NOT_FOUND);
		}
		else if (ex instanceof ResourceAlreadyExistsException rex) {
			return new ResponseEntity<String>(rex.getMessage(), HttpStatus.CONFLICT);
		}

		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
