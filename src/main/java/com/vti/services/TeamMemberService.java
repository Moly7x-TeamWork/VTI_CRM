//
package com.vti.services;

import java.util.List;

import com.vti.dto.TeamDTO;

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
}
