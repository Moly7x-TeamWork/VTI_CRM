//
package com.vti.services;

import java.util.List;
import java.util.Map;

import com.vti.dto.ITeamStudentDTO;

public interface TeamStudentService {
	
	/**
	 * 
	 * This method is find all student. 
	 * 
	 * @author: Tân
	 * @create_date: 16/09/2020
	 * @return List of Team student
	 */
	List<ITeamStudentDTO> findAllTeamStudent();
	
	/**
	 * 
	 * This method is div student to team or adviser. 
	 * 
	 * @author: Tân
	 * @create_date: 17/09/2020
	 * @param input map
	 * @return noti
	 */
	Map<String, String> addStudentToTeamAdviser(List<String> listIdTeamString, List<String> idAccountString, List<String> listPhoneNumberStudent);
}
