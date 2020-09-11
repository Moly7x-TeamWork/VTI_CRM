//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.TeamDTO;
import com.vti.entity.Account;
import com.vti.entity.RoleInTeam;
import com.vti.entity.Team;
import com.vti.entity.TeamMember;
import com.vti.exception.DataException;
import com.vti.repository.TeamMemberRepo;
import com.vti.services.AccountServices;
import com.vti.services.TeamMemberService;
import com.vti.services.TeamService;

/**
 * This class is implement of teamMemberSerivce. 
 * 
 * @author: Tân
 * @create_date: 11/09/2020
 */
@Service
public class TeamMemberServiceIMP implements TeamMemberService{
	
	@Autowired
	private TeamMemberRepo teamMemberRepo;
	
	@Autowired
	private AccountServices accountServices;
	
	@Autowired
	private TeamService teamService;
	
	/* 
	* @see com.vti.services.TeamMemberService#findInfoLeaderTeam()
	*/
	@Override
	public List<TeamDTO> findInfoLeaderTeam() {
		return teamMemberRepo.findInfoLeaderTeam();
	}

	/* 
	* @see com.vti.services.TeamMemberService#findInfoByLeaderEmail(java.lang.String)
	*/
	@Override
	public TeamDTO findInfoByLeaderEmail(String email) {
		return teamMemberRepo.findInfoByLeaderEmail(email);
	}

	/* 
	* @see com.vti.services.TeamMemberService#createTeam(com.vti.dto.TeamDTO)
	*/
	@Override
	public TeamDTO createTeamWithLeader(TeamDTO teamDTO) {
		//Check email is exist or not, if not throw error
		Account checkAccount = accountServices.findByEmail(teamDTO.getEmail());
		if (checkAccount == null) {
			throw new DataException("Not found","This email is not registered");
		}
		
		//Check email has been leader or not
		TeamDTO checkTeamDTO = findInfoByLeaderEmail(teamDTO.getEmail());
		if (checkTeamDTO != null) {
			throw new DataException("Dupplicate email","This email has been used in another team");
		}
		
		//Create new team
		Team teamNew = teamService.createTeam(teamDTO.getTeamName());
		
		//Add leader to new team, save to database
		TeamMember teamMemberNew = new TeamMember(teamNew, checkAccount, RoleInTeam.Leader);
		teamMemberRepo.saveAndFlush(teamMemberNew);
		
		//Create TeamDTO to return
		TeamDTO returnTeamDTO = new TeamDTO(teamNew.getIdTeam(), teamNew.getTeamName(), checkAccount, teamNew.getCreationDate());
		return returnTeamDTO;
	}
	
}
