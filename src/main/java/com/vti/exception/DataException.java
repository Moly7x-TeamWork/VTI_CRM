//
package com.vti.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataException extends RuntimeException {

	private static final long serialVersionUID = 943826173286060973L;

	private String error;
	private String message;

}
