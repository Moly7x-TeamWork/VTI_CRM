//
package com.vti.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "studentinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	@Id	
	@Column(name = "phonenumber", nullable = false, length = 11, unique = true)
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "idsource")
	private Source source;
	
	@Column(name = "firstname", nullable = false)
	private String firstName;
	
	@Column(name = "lastname", nullable = false)
	private String lastName;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "email")
	private String email;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "birthdate", length = 4)
	private Date birthDate;
	
	@Column(name = "school", length = 100)
	private String school;
	
	@Column(name = "address", length = 200)
	private String address;
	
	@Column(name = "social", length = 200)
	private String social;
	
	@ManyToOne
	@JoinColumn(name = "idtarget")
	private Target target;
	
	@ManyToOne
	@JoinColumn(name = "idstatus")
	private Status status;
	
	@Column(name = "transhistory")
	private String transHistory;
	
	@Column(name = "createdate", columnDefinition = "DATETIME DEFAULT NOW()")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date createDate;
}
