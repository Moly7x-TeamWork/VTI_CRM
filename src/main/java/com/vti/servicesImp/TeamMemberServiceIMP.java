//
package com.vti.servicesImp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
import com.vti.dto.TeamMemberDTO;
import com.vti.entity.Account;
import com.vti.entity.RoleInTeam;
import com.vti.entity.Team;
import com.vti.entity.TeamMember;
import com.vti.exception.DataException;
import com.vti.repository.TeamMemberRepo;
import com.vti.services.AccountServices;
import com.vti.services.TeamMemberService;
import com.vti.services.TeamService;

/**
 * This class is implement of teamMemberSerivce.
 * 
 * @author: Tân
 * @create_date: 11/09/2020
 */
@Service
public class TeamMemberServiceIMP implements TeamMemberService {

	@Autowired
	private TeamMemberRepo teamMemberRepo;

	@Autowired
	private AccountServices accountServices;

	@Autowired
	private TeamService teamService;

	/*
	 * @see com.vti.services.TeamMemberService#findInfoLeaderTeam()
	 */
	@Override
	public List<TeamDTO> findInfoLeaderTeam() {
		return teamMemberRepo.findInfoLeaderTeam();
	}

	/*
	 * @see
	 * com.vti.services.TeamMemberService#findInfoByLeaderEmail(java.lang.String)
	 */
	@Override
	public TeamDTO findInfoByLeaderEmail(String email) {
		return teamMemberRepo.findInfoByLeaderEmail(email);
	}

	/*
	 * @see com.vti.services.TeamMemberService#createTeam(com.vti.dto.TeamDTO)
	 */
	@Override
	public TeamDTO createTeamWithLeader(TeamDTO teamDTO) {
		// Check email is exist or not, if not throw error
		Account checkAccount = accountServices.findByEmail(teamDTO.getEmail());
		if (checkAccount == null) {
			throw new DataException("Not found", "This email is not registered");
		}

		// Check email has been leader or not
		TeamDTO checkTeamDTO = findInfoByLeaderEmail(teamDTO.getEmail());
		if (checkTeamDTO != null) {
			throw new DataException("Dupplicate email", "This email has been used in another team");
		}

		// Create new team
		Team teamNew = teamService.createTeam(teamDTO.getTeamName());

		// Add leader to new team, save to database
		TeamMember teamMemberNew = new TeamMember(teamNew, checkAccount, RoleInTeam.Leader);
		teamMemberRepo.saveAndFlush(teamMemberNew);

		// Create TeamDTO to return
		TeamDTO returnTeamDTO = new TeamDTO(teamNew.getIdTeam(), teamNew.getTeamName(), checkAccount,
				teamNew.getCreationDate());
		return returnTeamDTO;
	}

	/*
	 * @see com.vti.services.TeamMemberService#findAllTeamMemberByTeamID(long)
	 */
	@Override
	public List<TeamMemberDTO> findAllTeamMemberByTeamID(long idTeam) {
		return teamMemberRepo.findAllTeamMemberByTeamID(idTeam);
	}

	/*
	 * @see com.vti.services.TeamMemberService#findAllAccountNotInTeamByTeamID(long)
	 */
	@Override
	public List<IAccountDTO> findAllAccountNotInTeamByTeamID(long idTeam) {
		return teamMemberRepo.findAllAccountNotInTeamByTeamID(idTeam);
	}

	/*
	 * @see com.vti.services.TeamMemberService#addAccountToTeam(java.util.List,
	 * java.util.List)
	 */
	@Override
	public Map<String, String> addAccountToTeam(List<Long> listIdTeam, List<Long> listIdAccount) {
		// Check listIdTeam only have one element
		if (listIdTeam.size() != 1) {
			throw new DataException("idTeam Error", "One and Only one idTeam in List");
		}

		// Check listIdAccount has at least one element
		if (listIdAccount.size() == 0) {
			throw new DataException("listIdAccount Error", "Must have more one element in listIdAccount");
		}

		// Check Team exist
		Team checkTeam = teamService.findTeamById(listIdTeam.get(0));
		if (checkTeam == null) {
			throw new DataException("Team", "Can't found this team");
		}

		// Get list idAccount of this team at now
		List<Long> listIdAccountInTeamNow = findAllTeamMemberByTeamID(checkTeam.getIdTeam()).stream()
				.map(s -> s.getIdAccount()).collect(Collectors.toList());

		int count = 0;
		// Save account to team member
		for (int i = 0; i < listIdAccount.size(); ++i) {
			// create account from id
			Account account = accountServices.findAccountById(listIdAccount.get(i));

			// if account not null and listAccountInTeam at now don't have that account =>
			// save it and count++
			if (account != null && listIdAccountInTeamNow.contains(account.getIdAccount()) == false) {
				// Create team member entity
				TeamMember teamMember = new TeamMember(checkTeam, account, RoleInTeam.Member);

				// Save it to database
				teamMemberRepo.saveAndFlush(teamMember);

				count++;
			}
		}

		// Create noti
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String timestamp = dateFormat.format(new Date());

		Map<String, String> noti = new HashMap<String, String>();
		// Warning or success
		if (count != listIdAccount.size()) {
			noti.put("message", "WARNING");
		} else {
			noti.put("message", "SUCCESS");
		}

		noti.put("details", "System has been added " + count + "/" + listIdAccount.size() + " account to "
				+ checkTeam.getTeamName() + " team");
		noti.put("timestamp", timestamp);
		return noti;
	}

	/*
	 * @see com.vti.services.TeamMemberService#deleteAccountFromTeam(java.util.List,
	 * java.util.List)
	 */
	@Override
	public Map<String, String> deleteAccountFromTeam(List<Long> listIdTeam, List<Long> listIdAccount) {
		// Check listIdTeam only have one element
		if (listIdTeam.size() != 1) {
			throw new DataException("idTeam Error", "One and Only one idTeam in List");
		}

		// Check listIdAccount has at least one element
		if (listIdAccount.size() == 0) {
			throw new DataException("listIdAccount Error", "Must have more one element in listIdAccount");
		}

		// Check Team exist
		Team checkTeam = teamService.findTeamById(listIdTeam.get(0));
		if (checkTeam == null) {
			throw new DataException("Team", "Can't found this team");
		}

		// Get list idAccount of this team at now
		List<Long> listIdAccountInTeamNow = findAllTeamMemberByTeamID(checkTeam.getIdTeam()).stream()
				.map(s -> s.getIdAccount()).collect(Collectors.toList());

		int count = 0;
		// Delete account from team member
		for (int i = 0; i < listIdAccount.size(); ++i) {
			// create account from id
			Account account = accountServices.findAccountById(listIdAccount.get(i));

			// if account not null and listAccountInTeam at now have that account and that
			// account is not leader(index != 0, index = -1 if not found => index > 0) =>
			// delete it and count++
			if (account != null && listIdAccountInTeamNow.indexOf(account.getIdAccount()) > 0) {
				// delete it from database
				teamMemberRepo.deleteMember(checkTeam, account);

				count++;
			}
		}

		// Create noti
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String timestamp = dateFormat.format(new Date());

		Map<String, String> noti = new HashMap<String, String>();
		// Warning or success
		if (count != listIdAccount.size()) {
			noti.put("message", "WARNING");
		} else {
			noti.put("message", "SUCCESS");
		}

		noti.put("details", "System has been deleted " + count + "/" + listIdAccount.size() + " account to "
				+ checkTeam.getTeamName() + " team");
		noti.put("timestamp", timestamp);
		return noti;
	}

}
