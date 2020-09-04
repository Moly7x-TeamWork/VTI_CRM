//
package com.vti.servicesImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.TeamDTO;
import com.vti.entity.Account;
import com.vti.entity.Team;
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

	@Override
	public TeamDTO createTeam(TeamDTO teamDTO, Account account) {
		Team team = new Team();
		team.setAccount(account);
		team.setTeamName(teamDTO.getTeamName());
		team.setCreationDate(new Date());
		teamRepo.saveAndFlush(team);
		return findInfoByEmail(teamDTO.getEmail());
	}

	@Override
	public TeamDTO findInfoByEmail(String email) {
		return teamRepo.findInfoByEmail(email);
	}

}
