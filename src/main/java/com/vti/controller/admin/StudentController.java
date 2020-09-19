//
package com.vti.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vti.services.StudentService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("students")
	public Map<String, String> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
		return studentService.importStudentByFile(file);
	}
}
