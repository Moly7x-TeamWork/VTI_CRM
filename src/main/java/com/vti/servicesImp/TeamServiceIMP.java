//
package com.vti.servicesImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.AccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.entity.Account;
import com.vti.entity.Team;
import com.vti.exception.DataException;
import com.vti.repository.TeamRepo;
import com.vti.services.TeamService;

@Service
public class TeamServiceIMP implements TeamService {

	@Autowired
	private TeamRepo teamRepo;

	@Autowired
	private AccountServicesIMP accountServices;

	@Override
	public List<TeamDTO> findAllInfoTeam() {
		return teamRepo.findAllInfoTeam();
	}

	@Override
	public TeamDTO findInfoByEmail(String email) {
		return teamRepo.findInfoByEmail(email);
	}

	@Override
	public TeamDTO createTeam(TeamDTO teamDTO, Account account) {
		//Check email is exist or not
		AccountDTO checkAccountDTO = accountServices.findInfoByEmail(teamDTO.getEmail());
		if (checkAccountDTO == null) {
			throw new DataException("Not found","This email is not registered");
		}
		
		//Check email has used or not
		TeamDTO checkTeamDTO = findInfoByEmail(teamDTO.getEmail());
		if (checkTeamDTO != null) {
			throw new DataException("Dupplicate email","This email has been used in another team");
		}
		
		//Create team
		Team team = new Team();
		team.setAccount(account);
		team.setTeamName(teamDTO.getTeamName());
		team.setCreationDate(new Date());
		teamRepo.saveAndFlush(team);
		return findInfoByEmail(teamDTO.getEmail());
	}
}
