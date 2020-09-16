//
package com.vti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is status class. 
 * 
 * @author: Tân
 * @create_date: 15/09/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idstatus")
	private long idStatus;
	
	@Column(name = "statusdetail")
	private String statusDetail;
}
