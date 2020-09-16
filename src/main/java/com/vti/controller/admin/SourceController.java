//
package com.vti.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.entity.Source;
import com.vti.services.SourceService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class SourceController {
	@Autowired
	private SourceService sourceService;

	// Get all status
	@GetMapping("source")
	public List<Source> getSource() {
		return sourceService.getAllSource();
	}
}
