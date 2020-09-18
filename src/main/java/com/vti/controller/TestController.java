//
package com.vti.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vti.repository.StudentRepo;
import com.vti.services.StudentService;
import com.vti.services.TeamStudentService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TestController {
	
	
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

	@PostMapping("test")
	public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
		
		System.out.println(file.getOriginalFilename());
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Path path = Paths.get(System.getProperty("user.dir"), "scr", "fileCSV");
		
		System.out.println(file.getSize());

		
		try {
			Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//				.path("/files/download/")
//				.path(fileName)
//				.toUriString();
//		return ResponseEntity.ok(fileDownloadUri);
		return null;
	}
}
