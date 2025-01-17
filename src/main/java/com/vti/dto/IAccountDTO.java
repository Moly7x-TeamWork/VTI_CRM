//
package com.vti.dto;

/**
 * 
 * This class is for findAllAccountNotInTeamByTeamID, gender is String not Enum. 
 * JPA interface projection
 * @author: Tân
 * @create_date: 21/09/2020
 */
public interface IAccountDTO {
	
	public long getIdAccount();

	public String getEmail();
	
	public String getNameRole();
	
	public String getLastName();
	
	public String getFirstName();
	
	public String getGender();
}
