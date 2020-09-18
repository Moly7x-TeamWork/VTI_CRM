//
package com.vti.dto;

import com.vti.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

	private String phoneNumber;
	
	private String source;
	
	private String firstName;
	
	private String lastName;
	
	private Gender gender;
	
	private String email;
	
	private String birthDate;
	
	private String school;
	
	private String address;
	
	private String social;
	
	private String target;
	
	private String status;
	
	private String transHistory;
}
