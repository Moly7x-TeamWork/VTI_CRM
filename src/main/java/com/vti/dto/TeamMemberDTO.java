//
package com.vti.dto;

import com.vti.entity.RoleInTeam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {
	private long idAccount;
	private String firstName;
	private String lastName;
	private String email;
	private RoleInTeam roleInTeam;
}
