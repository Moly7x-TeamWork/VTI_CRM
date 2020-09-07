//
package com.vti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.services.AccountServices;
import com.vti.services.RoleService;
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
	private TeamService teamServices;

	@GetMapping("accounts")
	public List<AccountDTO> getAccounts() {
		return accountServices.findAllInfoAccount();
	}

	@PostMapping("accounts")
	public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
		return accountServices.createAccount(accountDTO, roleService.findByNameRole(accountDTO.getNameRole()));

	}

	@GetMapping("teams")
	public List<TeamDTO> getTeams() {
		return teamServices.findAllInfoTeam();
	}
	
	@PostMapping("teams")
	public TeamDTO createTeam(@RequestBody TeamDTO teamDTO) {
		System.out.println(teamDTO);
		return teamServices.createTeam(teamDTO, accountServices.findByEmail(teamDTO.getEmail()));
	}
	
	@GetMapping("test")
	public void test(@RequestBody AccountDTO accountDTO) {
		System.out.println(accountDTO);
	}
}
