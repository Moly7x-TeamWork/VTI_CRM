//
package com.vti.services;

import java.util.List;

import com.vti.dto.TeamDTO;
import com.vti.entity.Account;

public interface TeamService {
	List<TeamDTO> findAllInfoTeam();
	
	TeamDTO findInfoByEmail(String email);
	
	TeamDTO createTeam(TeamDTO teamDTO, Account account);
}
