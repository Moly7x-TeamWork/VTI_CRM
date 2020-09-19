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
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.ITeamStudentDTO;
import com.vti.services.TeamStudentService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TeamStudentController {
	
	@Autowired
	private TeamStudentService teamStudentService;
	
//	@Autowired
//	private StudentService studentService;
	
	@GetMapping("students")
	public List<ITeamStudentDTO> findAllStudents() {
		return teamStudentService.findAllTeamStudent();
	}
	
	@PostMapping("students/divto")
	public Map<String, String> addStudentToTeamAdviser(@RequestBody Map<String, List<String>> input) {
		return teamStudentService.addStudentToTeamAdviser(input.get("idTeam"), input.get("idAccount"), input.get("listPhoneNumberStudent"));
	}
}
