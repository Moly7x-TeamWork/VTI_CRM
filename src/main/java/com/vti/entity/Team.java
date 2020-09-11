//
package com.vti.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teaminfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idteam")
	private long idTeam;
	
	@Column(name = "teamname")
	private String teamName;
	
	@Column(name = "creationdate", columnDefinition = "DATETIME DEFAULT NOW()")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	//For Creating new team and save it to database
	public Team(String teamName, Date creationDate) {
		this.teamName = teamName;
		this.creationDate = creationDate;
	}
}
