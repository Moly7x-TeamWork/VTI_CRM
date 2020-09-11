//
package com.vti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.entity.Team;
import com.vti.services.AccountServices;
import com.vti.services.RoleService;
import com.vti.services.TeamMemberService;
import com.vti.services.TeamService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class AminController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountServices accountServices;
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private TeamService teamService;
	
	//Account
	@GetMapping("accounts")
	public List<AccountDTO> getAccounts() {
		return accountServices.findAllInfoAccount();
	}

	@PostMapping("accounts")
	public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
		return accountServices.createAccount(accountDTO, roleService.findByNameRole(accountDTO.getNameRole()));

	}
	
	//Account search
	@GetMapping("accounts/search")
	public List<AccountDTO> searchAccount(@RequestParam("search") String key) {
		return accountServices.searchAccount(key);
	}
	
	//Teams
	@GetMapping("teams")
	public List<TeamDTO> getTeams() {
		return teamMemberService.findInfoLeaderTeam();
	}
	
	@PostMapping("teams")
	public TeamDTO createTeamWithLeader(@RequestBody TeamDTO teamDTO) {
		return teamMemberService.createTeamWithLeader(teamDTO);
	}
	
//	@GetMapping("test")
//	public void test(@RequestBody Map<String, ?> request) {
//		System.out.println(request.get("search"));
//	}
	
	@GetMapping("test")
	public Team test(@RequestBody TeamDTO team) {
		return teamService.findTeamByTeamName(team.getTeamName());
	}
}
