//
package com.vti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStudentDTO {
	
	private String firstName;
	
	private String lastName;
	
	private String source;
	
	private String phoneNumber;
	
	private String target;
	
	private String status;
	
	private String teamName;	
	
}
