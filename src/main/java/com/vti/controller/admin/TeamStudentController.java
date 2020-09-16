//
package com.vti.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.CreateStudentDTO;
import com.vti.dto.TeamStudentDTO;
import com.vti.services.StudentService;
import com.vti.services.TeamStudentService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TeamStudentController {
	
	@Autowired
	private TeamStudentService teamStudentService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("students")
	public List<TeamStudentDTO> findAllStudents() {
		return teamStudentService.findAllTeamStudent();
	}
	
	@PostMapping("students")
	public CreateStudentDTO createStudent(@RequestBody CreateStudentDTO studentDTO) {
		return studentService.createStudent(studentDTO);
	}
}
