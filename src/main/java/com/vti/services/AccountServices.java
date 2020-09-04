package com.vti.services;

import java.util.List;

import com.vti.dto.AccountDTO;
import com.vti.entity.Role;

public interface AccountServices {
	List<AccountDTO> findAllInfoAccount();
	
	AccountDTO createAccount(AccountDTO accountDTO, Role role);
}
