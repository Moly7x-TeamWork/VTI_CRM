//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.TeamStudentDTO;
import com.vti.repository.TeamStudentRepo;
import com.vti.services.TeamStudentService;

@Service
public class TeamStudentServiceIMP implements TeamStudentService{

	@Autowired
	TeamStudentRepo teamStudentRepo;
	
	/* 
	* @see com.vti.services.TeamStudentService#findAllTeamStudent()
	*/
	@Override
	public List<TeamStudentDTO> findAllTeamStudent() {
		return teamStudentRepo.findAllTeamStudent();
	}

}
