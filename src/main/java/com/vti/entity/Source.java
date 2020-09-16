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
 * This class is source entity. 
 * 
 * @author: Tân
 * @create_date: 15/09/2020
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsource")
	private long idSource;
	
	@Column(name = "sourcedetail")
	private String sourceDetail;
}
