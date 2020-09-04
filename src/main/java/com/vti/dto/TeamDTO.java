//
package com.vti.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vti.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
	private long idTeam;
	
	private String email;
	
	private String teamName;
	
	private String creationDate;

	public TeamDTO(long idTeam, String teamName, Account account, Date creationDate) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		this.idTeam = idTeam;
		this.email = account.getEmail();
		this.teamName = teamName;
		this.creationDate = dateFormat.format(creationDate);
	}
	
	
}
