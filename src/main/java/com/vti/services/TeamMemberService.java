//
package com.vti.services;

import java.util.List;
import java.util.Map;

import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.dto.TeamMemberDTO;

public interface TeamMemberService {
	/**
	 * Find All team and leader
	 * @return List of TeamDTO
	 */
	List<TeamDTO> findInfoLeaderTeam();
	
	/**
	 * Find team by leader email
	 * @return TeamDTO
	 */
	TeamDTO findInfoByLeaderEmail(String email);
	
	/**
	 * 
	 * This method is create team with leader email
	 * 
	 * @author: Tân
	 * @create_date: 11/09/2020
	 * @param teamDTO
	 * @return teamDTO, team has
	 */
	TeamDTO createTeamWithLeader(TeamDTO teamDTO);
	
	/**
	 * 
	 * This method is find all team member by team id.
	 * Leader always in index 0
	 * @author: Tân
	 * @create_date: 11/09/2020
	 * @param teamId
	 * @return list of team member, TeamMemberDTO
	 */
	List<TeamMemberDTO> findAllTeamMemberByTeamID(long idTeam);
	
	/**
	 * 
	 * This method is find all account not in team by team id. 
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param teamId
	 * @return list of team member, AccountDTO
	 */
	List<IAccountDTO> findAllAccountNotInTeamByTeamID(long idTeam);
	
	/**
	 * 
	 * This method is add account to team. 
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param listIdTeam but only has ONE element
	 * @param listIdAccount
	 * @return map of noti
	 */
	Map<String, String> addAccountToTeam(List<Long> listIdTeam, List<Long> listIdAccount);
	
	/**
	 * 
	 * This method is delete account from team. 
	 * 
	 * @author: Tân
	 * @create_date: 13/09/2020
	 * @param listIdTeam but only has ONE element
	 * @param listIdAccount
	 * @return
	 */
	Map<String, String> deleteAccountFromTeam(List<Long> listIdTeam, List<Long> listIdAccount);
}
