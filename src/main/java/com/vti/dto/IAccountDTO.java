//
package com.vti.dto;

// for findAllAccountNotInTeamByTeamID, gender is String not Enum
public interface IAccountDTO {
	
	public long getIdAccount();

	public String getEmail();
	
	public String getNameRole();
	
	public String getLastName();
	
	public String getFirstName();
	
	public String getGender();
}
