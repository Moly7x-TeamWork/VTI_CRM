//
package com.vti.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.dto.TeamMemberDTO;
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
	
	//TeamsDetails
	@GetMapping("teamDetails")
	public List<TeamMemberDTO> getTeamDetails(@RequestParam("idTeam") String idTeam){
		return teamMemberService.findAllTeamMemberByTeamID(Long.parseLong(idTeam));
	}
	
	@PostMapping("teamDetails")
	public Map<String, String> addAccountToTeam(@RequestBody Map<String, List<Long>> request){
		return teamMemberService.addAccountToTeam(request.get("idTeam"), request.get("listIdAccount"));
	}
	
	@DeleteMapping("teamDetails")
	public Map<String, String> deleteAccountFromTeam(@RequestBody Map<String, List<Long>> request){
		return teamMemberService.deleteAccountFromTeam(request.get("idTeam"), request.get("listIdAccount"));
	}
	
	@GetMapping("teamDetails/accountNotInTeam")
	public List<IAccountDTO> getTeamDetailsAccountNotInTeam(@RequestParam("idTeam") String idTeam){
		return teamMemberService.findAllAccountNotInTeamByTeamID(Long.parseLong(idTeam));
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
