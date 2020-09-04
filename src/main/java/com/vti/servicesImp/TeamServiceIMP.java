//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.TeamDTO;
import com.vti.repository.TeamRepo;
import com.vti.services.TeamService;

@Service
public class TeamServiceIMP implements TeamService{

	@Autowired
	private TeamRepo teamRepo;
	
	@Override
	public List<TeamDTO> findAllInfoTeam() {
		return teamRepo.findAllInfoTeam();
	}

}
