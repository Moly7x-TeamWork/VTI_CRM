//
package com.vti.dto;

import java.util.Calendar;

import com.vti.entity.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
	private long idTeam;
	
	private String email;
	
	private String teamName;
	
	private Calendar creationDate;

	public TeamDTO(long idTeam, String teamName, Account account, Calendar creationDate) {
		this.idTeam = idTeam;
		this.email = account.getEmail();
		this.teamName = teamName;
		this.creationDate = creationDate;
	}
	
	
}
