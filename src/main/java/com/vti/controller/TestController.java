//
package com.vti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.TeamStudentDTO;
import com.vti.repository.StudentRepo;
import com.vti.services.StudentService;
import com.vti.services.TeamService;
import com.vti.services.TeamStudentService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TestController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeamStudentService teamStudentService;
	
	@Autowired
	StudentRepo studentRepo;
	
//	@GetMapping("test")
//	public void test(@RequestBody Map<String, ?> request) {
//		System.out.println(request.get("search"));
//	}

	@GetMapping("test")
	public List<TeamStudentDTO> findAllStudent() {
		return teamStudentService.findAllTeamStudent();
	}
}
