//
package com.vti.controller;

import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
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
public class TestController {

//	@Autowired
//	private TeamStudentService teamStudentService;

//	@GetMapping("test")
//	public void test(@RequestBody Map<String, ?> request) {
//		System.out.println(request.get("search"));
//	}

	@PostMapping("testUpload")
	public Map<String, Object> uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
		Map<String, Object> noti = new LinkedMap<>();
		noti.put("fileName", file.getOriginalFilename());
		noti.put("fileSize", file.getSize());
		return noti;
	}
}
