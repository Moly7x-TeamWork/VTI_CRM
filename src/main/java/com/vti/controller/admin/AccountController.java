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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.dto.IAccountDetail;
import com.vti.services.AccountServices;
import com.vti.services.RoleService;

@RestController
@RequestMapping("api/admin/v1")
@CrossOrigin(origins = "*")
public class AccountController {
	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountServices accountServices;

	// Account
	@GetMapping("accounts")
	public List<IAccountDetail> getAccounts() {
		return accountServices.findAllInfoAccount();
	}

	@PostMapping("accounts")
	public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
		return accountServices.createAccount(accountDTO, roleService.findByNameRole(accountDTO.getNameRole()));

	}

	// Account search
	@GetMapping("accounts/search")
	public List<Map<String, Object>> searchAccount(@RequestParam("search") String key) {
		return accountServices.searchAccount(key);
	}
}
