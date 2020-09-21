//
package com.vti.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(DataException.class)
	public ResponseEntity<ErrorDetails> handlerDataException(DataException exception, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getError(), exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorDetails> handlerParameterException(Exception exception, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Required parameter",exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<ErrorDetails> handlerMultipartException(Exception exception, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Required file", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorDetails> handlerDataException(Exception exception, WebRequest request){
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.toString() ,exception.getMessage());
//		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
//	}
}
