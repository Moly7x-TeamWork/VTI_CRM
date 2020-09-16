//
package com.vti.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Status;
import com.vti.services.StatusService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class StatusController {
	@Autowired
	private StatusService statusService;

	// Get all status
	@GetMapping("status")
	public List<Status> getStatus() {
		return statusService.getAllStatus();
	}
}
