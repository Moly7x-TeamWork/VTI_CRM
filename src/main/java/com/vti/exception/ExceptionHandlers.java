//
package com.vti.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(DataException.class)
	public ResponseEntity<ErrorDetails> handlerDataException(DataException exception, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getError(), exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
