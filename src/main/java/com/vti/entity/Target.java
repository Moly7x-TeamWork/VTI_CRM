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
 * This class is target entity. 
 * 
 * @author: Tân
 * @create_date: 15/09/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Target {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtarget")
	private long idTarget;
	
	@Column(name = "targetdetail")
	private String targetDetail;
}
