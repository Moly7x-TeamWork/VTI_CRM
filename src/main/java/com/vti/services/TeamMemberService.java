//
package com.vti.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.entity.Account;
import com.vti.entity.TeamMember;

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
	 * This method is check this account has belong to any team or not. 
	 * 
	 * @author: Tân
	 * @create_date: 17/09/2020
	 * @param account
	 * @return
	 */
	Optional<List<TeamMember>> findByAccount(Account account);
	
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
	 * @return list of team member and info of that team
	 * @exception show error if idTeam is null or character
	 */
	Map<String, Object> findAllTeamMemberByTeamID(String idTeamString);
	
	/**
	 * 
	 * This method is find all account not in team by team id. 
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param teamId
	 * @return list of team member, AccountDTO
	 */
	List<IAccountDTO> findAllAccountNotInTeamByTeamID(String idTeam);
	
	/**
	 * 
	 * This method is find all account not in All team . 
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param teamId
	 * @return list of team member, AccountDTO
	 */
	List<IAccountDTO> findAllAccountNotInAllTeam();
	
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
	Map<String, String> addAccountToTeam(List<String> listIdTeamString, List<String> listIdAccountString);
	
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
	Map<String, String> deleteAccountFromTeam(List<String> listIdTeamString, List<String> listIdAccountString);
}
