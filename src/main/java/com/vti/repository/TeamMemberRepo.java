//
package com.vti.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.dto.TeamMemberDTO;
import com.vti.entity.Account;
import com.vti.entity.Team;
import com.vti.entity.TeamMember;
import com.vti.entity.TeamMemberID;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, TeamMemberID> {
	// find all team and leader email
	@Query("SELECT new com.vti.dto.TeamDTO(Team.idTeam, Team.teamName, tm.account, Team.creationDate) FROM TeamMember tm JOIN tm.team Team JOIN tm.account Account WHERE tm.roleInTeam = 'Leader' ORDER BY Team.idTeam")
	List<TeamDTO> findInfoLeaderTeam();

	// find Team by email leader
	@Query("SELECT new com.vti.dto.TeamDTO(Team.idTeam, Team.teamName, tm.account, Team.creationDate) FROM TeamMember tm JOIN tm.team Team JOIN tm.account Account WHERE Account.email = :email")
	TeamDTO findInfoByLeaderEmail(@Param("email") String email);

	// find all member IN team by ID teams, leader always in index 0
	@Query("SELECT new com.vti.dto.TeamMemberDTO(Account.idAccount, Account.firstName, Account.lastName, Account.email, tm.roleInTeam) FROM TeamMember tm JOIN tm.account Account WHERE tm.team.idTeam = :idTeam ORDER BY tm.roleInTeam")
	List<TeamMemberDTO> findAllTeamMemberByTeamID(@Param("idTeam") long idTeam);

	// find all member NOT IN team by ID teams
	@Query(value = "CALL getListMemberNotInTeam(:idTeam)", nativeQuery = true)
	List<IAccountDTO> findAllAccountNotInTeamByTeamID(@Param("idTeam") long idTeam);
	
	//delete member by idTeam and idAccount
	@Transactional
    @Modifying
    @Query("DELETE FROM TeamMember tm WHERE tm.team = :team AND tm.account = :account")
	int deleteMember(@Param("team") Team team, @Param("account") Account account);
}
