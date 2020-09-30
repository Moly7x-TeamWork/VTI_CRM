//
package com.vti.dto;

/**
 * This class is get all account. 
 * JPA interface projection
 * @author: Tân
 * @create_date: 21/09/2020
 */
public interface IAccountDetail {
	
	public long getIdAccount();

	public String getEmail();
	
	public String getFirstName();
	
	public String getLastName();
	
	public String getGender();
	
	public String getTeamName();
	
	public String getNameRole();
	
	public String setEmail(String email);

}
