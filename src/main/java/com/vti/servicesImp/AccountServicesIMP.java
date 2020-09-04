//
package com.vti.servicesImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.entity.Role;
import com.vti.repository.AccountRepo;
import com.vti.services.AccountServices;

@Service
public class AccountServicesIMP implements AccountServices {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public List<AccountDTO> findAllInfoAccount() {
		return accountRepo.findAllInfoAccount();
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO, Role role) {
//		accountRepo.findByEmail(account.getEmail()
		Account account = new Account();
		account.setRole(role);
		account.setEmail(accountDTO.getEmail());
		account.setPassword(accountDTO.getPassword());
		account.setLastName(accountDTO.getLastName());
		account.setFirstName(accountDTO.getFirstName());
		account.setGender(accountDTO.getGender());
		System.out.println(account);
		accountRepo.saveAndFlush(account);
		return accountRepo.findByEmail(account.getEmail());
	}

}
