//
package com.vti.dto;

import com.vti.entity.Gender;
import com.vti.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This class is for creating account. 
 * 
 * @author: Tân
 * @create_date: 21/09/2020
 */
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
	public AccountDTO(long idAccount, String email) {
		this.idAccount = idAccount;
		this.email = email;
	}
}
