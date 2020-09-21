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
public interface TeamRepo extends JpaRepository<Team, Long> {

	// find Team by teamName
	@Query("SELECT t FROM Team t WHERE t.teamName = :teamName")
	Team findTeamByTeamName(@Param("teamName") String teamName);

	// find Team by id
	@Query("SELECT t FROM Team t WHERE t.idTeam = :idTeam")
	Team findTeamById(@Param("idTeam") long idTeam);

	// Search team by keyword, search in team name
	@Query("SELECT new com.vti.dto.TeamDTO(t.idTeam, t.teamName) FROM Team t WHERE t.teamName LIKE :key%")
	List<TeamDTO> searchTeambyKeyWord(@Param("key") String key);
}
