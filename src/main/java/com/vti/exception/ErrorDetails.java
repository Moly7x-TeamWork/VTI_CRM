//
package com.vti.exception;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
	
	private String timestamp;
	private String error;
	private String message;

	public ErrorDetails(Date timestamp, String error, String message) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		this.timestamp = dateFormat.format(timestamp);
		this.error = error;
		this.message = message;
	}
	
}
