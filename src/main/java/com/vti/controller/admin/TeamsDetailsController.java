//
package com.vti.controller.admin;

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

import com.vti.dto.IAccountDTO;
import com.vti.services.TeamMemberService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TeamsDetailsController {
	
	@Autowired
	private TeamMemberService teamMemberService;

	// TeamsDetails
	@GetMapping("teamDetails")
	public Map<String, Object> getTeamDetails(@RequestParam("idTeam") String idTeam) {
		return teamMemberService.findAllTeamMemberByTeamID(idTeam);
	}

	@GetMapping("teamDetails/accountNotInTeam")
	public List<IAccountDTO> getTeamDetailsAccountNotInTeam(@RequestParam("idTeam") String idTeam) {
		return teamMemberService.findAllAccountNotInTeamByTeamID(idTeam);
	}

	@PostMapping("teamDetails")
	public Map<String, String> addAccountToTeam(@RequestBody Map<String, List<String>> request) {
		return teamMemberService.addAccountToTeam(request.get("idTeam"), request.get("listIdAccount"));
	}

	@DeleteMapping("teamDetails")
	public Map<String, String> deleteAccountFromTeam(@RequestBody Map<String, List<String>> request) {
		return teamMemberService.deleteAccountFromTeam(request.get("idTeam"), request.get("listIdAccount"));
	}
}
