//
package com.vti.servicesImp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.AccountDTO;
import com.vti.dto.IAccountDetail;
import com.vti.entity.Account;
import com.vti.entity.Role;
import com.vti.exception.DataException;
import com.vti.repository.AccountRepo;
import com.vti.repository.TeamMemberRepo;
import com.vti.services.AccountServices;

@Service
public class AccountServicesIMP implements AccountServices {

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private TeamMemberRepo teamMemberRepo;

	@Override
	public List<IAccountDetail> findAllInfoAccount() {
		//return accountRepo.findAllInfoAccount();
		return teamMemberRepo.findAllAccountsDetail();
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO, Role role) {
		//Check email exist or not
		AccountDTO checkAcccountDTO = accountRepo.findInfoByEmail(accountDTO.getEmail());
		if (checkAcccountDTO != null) {
			throw new DataException("Duplicated email", "This email has been used");
		}
		
		//If not, create account
		Account account = new Account();
		account.setRole(role);
		account.setEmail(accountDTO.getEmail());
		account.setPassword(accountDTO.getPassword());
		account.setLastName(accountDTO.getLastName());
		account.setFirstName(accountDTO.getFirstName());
		account.setGender(accountDTO.getGender());
		accountRepo.saveAndFlush(account);
		return findInfoByEmail(account.getEmail());
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepo.findByEmail(email);
	}

	@Override
	public AccountDTO findInfoByEmail(String email) {
		return accountRepo.findInfoByEmail(email);
	}

	/* 
	* @see com.vti.services.AccountServices#searchAccount(java.lang.String)
	*/
	@Override
	public List<Map<String, Object>> searchAccount(String key) {
//		//Check length key word, if zero or null or only whitespace, throw exception
//		if (key == null || key.isBlank() || key.isEmpty()) {
//			throw new DataException("Wrong text search", "This text search is null or only has whitespace");
//		}
		
		// search
		List<AccountDTO> accountList = accountRepo.searchAccountbyKeyWord(key);
		
		// create result
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < accountList.size(); ++i) {
			result.add(new TreeMap<String, Object>(Collections.reverseOrder()));
			result.get(i).put("email", accountList.get(i).getEmail());
			result.get(i).put("idAccount", accountList.get(i).getIdAccount());
		}
		
		return result;
	}

	/* 
	* @see com.vti.services.AccountServices#findTeamById(long)
	*/
	@Override
	public Account findAccountById(long idAccount) {
		return accountRepo.findAccountById(idAccount);
	}
}
