//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.dto.TeamDTO;
import com.vti.entity.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long>{
	
	/*
	 * find all account in database, and connect to account id
	*/
	@Query("SELECT new com.vti.dto.TeamDTO(t.idTeam, t.teamName, t.account, t.creationDate) FROM Team t JOIN t.account Account ORDER BY t.idTeam")
	List<TeamDTO> findAllInfoTeam();
	
	@Query("SELECT new com.vti.dto.TeamDTO(t.idTeam, t.teamName, t.account, t.creationDate) FROM Team t JOIN t.account Account WHERE Account.email = :email")
	TeamDTO findInfoByEmail(@Param("email") String email);
}
