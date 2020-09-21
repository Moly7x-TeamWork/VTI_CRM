//
package com.vti.services;

import java.util.List;
import java.util.Map;

import com.vti.entity.Team;

public interface TeamService {	
	/**
	 * 
	 * This method is find team by teamname and return team has just found. 
	 * It will return null if not found
	 * 
	 * @author: Tân
	 * @create_date: 11/09/2020
	 * @param teamName
	 * @return Team
	 */
	Team findTeamByTeamName(String teamName);
	
	/**
	 * 
	 * This method is find team by ID and return team has just found. 
	 * It will return null if not found
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param idTeam
	 * @return Team
	 */
	Team findTeamById(long idTeam);
	
	/**
	 * 
	 * This method is create new team by Teamname.
	 * It will throw exception if teamName has been use.
	 * If not, it will create team 
	 * 
	 * @author: Tân
	 * @create_date: 11/09/2020
	 * @param teamName
	 * @return Team
	 */
	Team createTeam(String teamName);
	
	/**
	 * 
	 * This method is search team by teamname and return list of team has just found.
	 * 
	 * @author: Tân
	 * @create_date: 21/09/2020
	 * @param teamName
	 * @return list of Team
	 */
	List<Map<String, String>> searchTeamByTeamName(String key);
}
