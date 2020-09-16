//
package com.vti.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Target;
import com.vti.services.TargetService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class TargetController {
	@Autowired
	private TargetService targetService;

	// Get all status
	@GetMapping("target")
	public List<Target> gettarget() {
		return targetService.getAllTarget();
	}
}
