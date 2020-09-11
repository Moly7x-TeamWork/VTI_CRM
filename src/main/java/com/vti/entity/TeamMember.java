//
package com.vti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teammember")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TeamMemberID.class)
public class TeamMember {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "idteam")
	private Team team;	
	
	@Id
	@ManyToOne
	@JoinColumn(name = "idaccount")
	private Account account;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "roleinteam")
	private RoleInTeam roleInTeam;
}
