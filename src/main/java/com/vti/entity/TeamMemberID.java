//
package com.vti.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberID implements Serializable{
	//
	private static final long serialVersionUID = -9065248539418631270L;

	private long team;
	
	private long account;
}
