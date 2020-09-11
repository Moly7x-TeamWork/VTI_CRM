//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.dto.TeamDTO;
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
}
