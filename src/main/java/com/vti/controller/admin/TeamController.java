//
package com.vti.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.TeamDTO;
import com.vti.services.TeamMemberService;
import com.vti.services.TeamService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TeamController {

	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private TeamService teamService;

	// Teams
	@GetMapping("teams")
	public List<TeamDTO> getTeams() {
		return teamMemberService.findInfoLeaderTeam();
	}

	@PostMapping("teams")
	public TeamDTO createTeamWithLeader(@RequestBody TeamDTO teamDTO) {
		return teamMemberService.createTeamWithLeader(teamDTO);
	}
	
	// team search
	@GetMapping("teams/search")
	public List<Map<String, String>> searchAccount(@RequestParam("search") String key) {
		return teamService.searchTeamByTeamName(key);
	}
}
