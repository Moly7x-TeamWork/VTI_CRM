//
package com.vti.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teamstudent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStudent {
	
	@Id
    private String phoneNumber;
	
	@OneToOne
	@JoinColumn(name = "phonenumber")
	@MapsId
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "idaccount", nullable = true)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "idteam", nullable = true)
	private Team team;
}
