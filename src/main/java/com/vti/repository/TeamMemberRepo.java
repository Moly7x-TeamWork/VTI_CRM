//
package com.vti.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.dto.IAccountDTO;
import com.vti.dto.IAccountDetail;
import com.vti.dto.TeamDTO;
import com.vti.dto.TeamMemberDTO;
import com.vti.entity.Account;
import com.vti.entity.Team;
import com.vti.entity.TeamMember;
import com.vti.entity.TeamMemberID;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, TeamMemberID> {

	// find all account, in team and not in team
	@Query(value = "SELECT Account.idAccount, Account.email, Account.firstName, Account.lastName, Account.gender, GROUP_CONCAT(ti.teamName SEPARATOR ', ') teamName, `role`.nameRole FROM teamMember tm JOIN teamInfo ti ON tm.idteam = ti.idteam RIGHT JOIN Account on tm.idAccount = Account.idAccount JOIN `role` on `role`.idRole = Account.idrole GROUP BY Account.idAccount ORDER BY role.nameRole;", nativeQuery = true)
	List<IAccountDetail> findAllAccountsDetail();

	// find all team and leader email
	@Query("SELECT new com.vti.dto.TeamDTO(Team.idTeam, Team.teamName, tm.account, Team.creationDate) FROM TeamMember tm JOIN tm.team Team JOIN tm.account Account WHERE tm.roleInTeam = 'Leader' ORDER BY Team.idTeam")
	List<TeamDTO> findInfoLeaderTeam();

	// find Team by email leader
	@Query("SELECT new com.vti.dto.TeamDTO(Team.idTeam, Team.teamName, tm.account, Team.creationDate) FROM TeamMember tm JOIN tm.team Team JOIN tm.account Account WHERE Account.email = :email")
	TeamDTO findInfoByLeaderEmail(@Param("email") String email);

	// check this account has belong to any team or not
	Optional<List<TeamMember>> findByAccount(Account account);

	// find all member IN team by ID teams, leader always in index 0
	@Query("SELECT new com.vti.dto.TeamMemberDTO(Account.idAccount, Account.firstName, Account.lastName, Account.email, tm.roleInTeam) FROM TeamMember tm JOIN tm.account Account WHERE tm.team.idTeam = :idTeam ORDER BY tm.roleInTeam")
	List<TeamMemberDTO> findAllTeamMemberByTeamID(@Param("idTeam") long idTeam);

	// find all member NOT IN team by ID teams
	@Query(value = "CALL getListMemberNotInTeam(:idTeam)", nativeQuery = true)
	List<IAccountDTO> findAllAccountNotInTeamByTeamID(@Param("idTeam") long idTeam);

	// find all member NOT IN all team
	@Query(value = "SELECT Account.idAccount, Account.email, Account.firstName, Account.lastName, Account.gender, `role`.nameRole FROM teamMember tm RIGHT JOIN Account on tm.idAccount = Account.idAccount JOIN `role` on `role`.idRole = Account.idrole  WHERE tm.idTeam IS NULL ORDER BY role.nameRole;", nativeQuery = true)
	List<IAccountDTO> findAllAccountNotInAllTeam();

	// delete member by idTeam and idAccount
	@Transactional
	@Modifying
	@Query("DELETE FROM TeamMember tm WHERE tm.team = :team AND tm.account = :account")
	int deleteMember(@Param("team") Team team, @Param("account") Account account);
}
