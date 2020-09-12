//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	/*
	 * find all account in database, but it will except token, password
	 */
	@Query("SELECT new com.vti.dto.AccountDTO(ac.idAccount, ac.email, ac.role, ac.firstName, ac.lastName, ac.gender) FROM Account ac JOIN ac.role Role ORDER BY ac.idAccount")
	List<AccountDTO> findAllInfoAccount();

	@Query("SELECT new com.vti.dto.AccountDTO(ac.idAccount, ac.email, ac.role, ac.firstName, ac.lastName, ac.gender) FROM Account ac JOIN ac.role Role WHERE ac.email = :email")
	AccountDTO findInfoByEmail(@Param("email") String email);

	Account findByEmail(@Param("email") String email);

	// find account by Id
	@Query("SELECT a FROM Account a WHERE a.idAccount = :idAccount")
	Account findAccountById(@Param("idAccount") long idAccount);

	// Search account by keyword, search in email
	@Query("SELECT new com.vti.dto.AccountDTO(ac.idAccount, ac.email) FROM Account ac WHERE ac.email LIKE :key%")
	List<AccountDTO> searchAccountbyKeyWord(@Param("key") String key);
}
