//
package com.vti.dto;

import com.vti.entity.Gender;
import com.vti.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO{
	
	private long idAccount;
	
	private String email;
	
	//for create account
	private String password;
	
	private String nameRole;
	
	private String firstName;
	
	private String lastName;
	
	private Gender gender;
	
	// for everything, convert from Role to nameRole
	public AccountDTO(long idAccount, String email, Role role, String firstName, String lastName, Gender gender) {
		this.idAccount = idAccount;
		this.email = email;
		this.nameRole = role.getNameRole();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	// use in searchAccountbyKeyWord
	public AccountDTO(String email) {
		this.email = email;
	}
}
