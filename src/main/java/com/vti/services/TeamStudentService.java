//
package com.vti.services;

import java.util.List;

import com.vti.dto.TeamStudentDTO;

public interface TeamStudentService {
	
	/**
	 * 
	 * This method is find all student. 
	 * 
	 * @author: Tân
	 * @create_date: 16/09/2020
	 * @return List of Team student
	 */
	List<TeamStudentDTO> findAllTeamStudent();
}
