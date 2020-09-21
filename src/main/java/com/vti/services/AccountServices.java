package com.vti.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.vti.dto.AccountDTO;
import com.vti.dto.IAccountDetail;
import com.vti.entity.Account;
import com.vti.entity.Role;

public interface AccountServices {
	List<IAccountDetail> findAllInfoAccount();
	
	Account findByEmail(String email);
	
	AccountDTO findInfoByEmail(@Param("email") String email);
	
	AccountDTO createAccount(AccountDTO accountDTO, Role role);
	
	/**
	 * 
	 * Search account by keyword in email
	 * 
	 * @author: Tân
	 * @create_date: 15/09/2020
	 * @param key
	 * @return list account has only idAccount and email
	 */
	List<Map<String, Object>> searchAccount(String key);
	
	/**
	 * 
	 * This method is find account by ID. 
	 * 
	 * @author: Tân
	 * @create_date: 12/09/2020
	 * @param idAccount
	 * @return Account
	 */
	Account findAccountById(long idAccount);
}
