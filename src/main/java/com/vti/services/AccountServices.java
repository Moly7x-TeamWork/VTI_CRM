package com.vti.services;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.entity.Role;

public interface AccountServices {
	List<AccountDTO> findAllInfoAccount();
	
	Account findByEmail(String email);
	
	AccountDTO findInfoByEmail(@Param("email") String email);
	
	AccountDTO createAccount(AccountDTO accountDTO, Role role);
	
	//Search account by keyword in email
	List<AccountDTO> searchAccount(String key);
}
