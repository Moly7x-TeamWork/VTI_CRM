//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vti.dto.TeamStudentDTO;
import com.vti.entity.TeamStudent;

@Repository
public interface TeamStudentRepo extends JpaRepository<TeamStudent, String>{
	
	//find all team student and select into TeamstudentDTO
	@Query("SELECT new com.vti.dto.TeamStudentDTO(ts.student.firstName, ts.student.lastName, ts.student.source.sourceDetail, ts.student.phoneNumber, ts.student.target.targetDetail, ts.student.status.statusDetail, ts.team.teamName) FROM TeamStudent ts ORDER BY ts.team.idTeam")
	List<TeamStudentDTO> findAllTeamStudent();
	
}
