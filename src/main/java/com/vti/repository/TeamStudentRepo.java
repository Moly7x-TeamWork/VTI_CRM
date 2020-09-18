//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vti.dto.ITeamStudentDTO;
import com.vti.entity.TeamStudent;

@Repository
public interface TeamStudentRepo extends JpaRepository<TeamStudent, String>, TeamStudentRepoCustom{
	
	@Query(value = "SELECT si.firstName, si.lastName, so.sourceDetail `source`, si.phoneNumber, tg.targetDetail targets, st.statusDetail `status`, ti.teamName FROM teamStudent ts RIGHT JOIN studentInfo si on ts.phoneNumber = si.phoneNumber LEFT JOIN teamInfo ti on ts.idTeam = ti.idTeam LEFT JOIN Target tg ON tg.idTarget = si.idTarget LEFT JOIN `status` st ON st.idStatus = si.idStatus LEFT JOIN `source` so ON so.idSource = si.idSource ORDER BY ti.teamName DESC;", nativeQuery = true)
	List<ITeamStudentDTO> findAllTeamStudent();
	
}
