//
package com.vti.servicesImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.TeamDTO;
import com.vti.entity.Team;
import com.vti.exception.DataException;
import com.vti.repository.TeamRepo;
import com.vti.services.TeamService;

@Service
public class TeamServiceIMP implements TeamService {

	@Autowired
	private TeamRepo teamRepo;

	/*
	 * @see com.vti.services.TeamService#findTeamByTeamName(java.lang.String)
	 */
	@Override
	public Team findTeamByTeamName(String teamName) {
		return teamRepo.findTeamByTeamName(teamName);
	}

	/*
	 * @see com.vti.services.TeamService#createTeam(java.lang.String)
	 */
	@Override
	public Team createTeam(String teamName) {
		// Check team name has been used or not, if yes throw error
		Team checkTeam = findTeamByTeamName(teamName);
		if (checkTeam != null) {
			throw new DataException("Dupplicate team name", "This team name has been used in another team");
		}

		// Create new team and save it to database
		Team teamNew = new Team(teamName, new Date());
		return teamRepo.saveAndFlush(teamNew);
	}

	/*
	 * @see com.vti.services.TeamService#findTeamById(long)
	 */
	@Override
	public Team findTeamById(long idTeam) {
		return teamRepo.findTeamById(idTeam);
	}

	/*
	 * @see com.vti.services.TeamService#searchTeamByTeamName(java.lang.String)
	 */
	@Override
	public List<Map<String, String>> searchTeamByTeamName(String key) {
		// get list team found
		List<TeamDTO> listTeam = teamRepo.searchTeambyKeyWord(key);

		// get only need field
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		;
		listTeam.forEach(x -> {
			result.add(new LinkedMap<String, String>());
			result.get(result.size() - 1).put("idTeam", Long.valueOf(x.getIdTeam()).toString());
			result.get(result.size() - 1).put("teamName", x.getTeamName());
		});

		return result;
	}
}
