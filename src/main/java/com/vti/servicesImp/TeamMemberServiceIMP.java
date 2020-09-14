//
package com.vti.servicesImp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.IAccountDTO;
import com.vti.dto.TeamDTO;
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
	public Map<String, Object> findAllTeamMemberByTeamID(String idTeamString) {
		// Convert idTeamString to idTeam
		long idTeam = 0;
		try {
			idTeam = Long.parseLong(idTeamString);
		} catch (Exception e) {
			throw new DataException("idTeam Error", "idTeam can't be character or null");
		}

		// Check team exist or not
		if (teamService.findTeamById(idTeam) == null) {
			throw new DataException("Team", "Can't find this team");
		}

		// Create the return use treemap cause it's ordered
		Map<String, Object> teamDetails = new TreeMap<String, Object>();
		teamDetails.put("teamInfo", new TeamDTO(teamService.findTeamById(idTeam)));
		teamDetails.put("teamMember", teamMemberRepo.findAllTeamMemberByTeamID(idTeam));

		return teamDetails;
	}

	/*
	 * @see com.vti.services.TeamMemberService#findAllAccountNotInTeamByTeamID(long)
	 */
	@Override
	public List<IAccountDTO> findAllAccountNotInTeamByTeamID(String idTeamString) {
		// Convert idTeamString to idTeam
		long idTeam = 0;
		try {
			idTeam = Long.parseLong(idTeamString);
		} catch (Exception e) {
			throw new DataException("idTeam Error", "idTeam can't be character or null");
		}

		// Check team exist or not
		if (teamService.findTeamById(idTeam) == null) {
			throw new DataException("Team", "Can't find this team");
		}

		return teamMemberRepo.findAllAccountNotInTeamByTeamID(idTeam);
	}

	/*
	 * @see com.vti.services.TeamMemberService#addAccountToTeam(java.util.List,
	 * java.util.List)
	 */
	@Override
	public Map<String, String> addAccountToTeam(List<String> listIdTeamString, List<String> listIdAccountString) {
		// Check listIdTeam only have one element
		if (listIdTeamString.size() != 1) {
			throw new DataException("idTeam Error", "One and Only one idTeam in List");
		}

		// Check listIdAccount has at least one element
		if (listIdAccountString.size() == 0) {
			throw new DataException("listIdAccount Error", "Must have more one element in listIdAccount");
		}

		// Convert list string id team to list long
		List<Long> listIdTeam = new ArrayList<>();
		try {
			listIdTeam = listIdTeamString.stream().map(Long::parseLong).collect(Collectors.toList());
		} catch (Exception e) {
			throw new DataException("idTeam Error", "idTeam can't be character or null");
		}

		// Convert list string id account to list long
		List<Long> listIdAccount = new ArrayList<>();
		try {
			listIdAccount = listIdAccountString.stream().map(Long::parseLong).collect(Collectors.toList());
		} catch (Exception e) {
			throw new DataException("idAccount Error", "idAccount can't be character or null");
		}

		// Check Team exist
		Team checkTeam = teamService.findTeamById(listIdTeam.get(0));
		if (checkTeam == null) {
			throw new DataException("Team", "Can't find this team");
		}

		// Get list idAccount of this team at now by team repo
		List<Long> listIdAccountInTeamNow = teamMemberRepo.findAllTeamMemberByTeamID(checkTeam.getIdTeam()).stream()
				.map(s -> s.getIdAccount()).collect(Collectors.toList());

		int countAdded = 0;
		int countAccountNotExist = 0;
		int countAccountHasInTeam = 0;
		// Save account to team member
		for (int i = 0; i < listIdAccount.size(); ++i) {
			// find account from id
			Account account = accountServices.findAccountById(listIdAccount.get(i));

			// if account not null and listAccountInTeam at now don't have that account =>
			// save it and countAdded++
			if (account != null) {
				if (listIdAccountInTeamNow.contains(account.getIdAccount()) == false) {
					// Create team member entity
					TeamMember teamMember = new TeamMember(checkTeam, account, RoleInTeam.Member);

					// Save it to database
					teamMemberRepo.saveAndFlush(teamMember);

					countAdded++;
				} else {
					// if has been team, count it
					countAccountHasInTeam++;
				}
			} else {
				// if not found account, count it
				countAccountNotExist++;
			}
		}

		// Create noti
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String timestamp = dateFormat.format(new Date());

		Map<String, String> noti = new TreeMap<String, String>();

		// Warning or success
		if (countAdded != listIdAccount.size()) {
			noti.put("message", "WARNING");
		} else {
			noti.put("message", "SUCCESS");
		}

		// create details message
		String details = "System has been added " + countAdded + "/" + listIdAccount.size() + " account to "
				+ checkTeam.getTeamName() + " team.";

		if (countAccountNotExist != 0) {
			details = details + " You have selected " + countAccountNotExist + " accounts that don't exist.";
		}

		if (countAccountHasInTeam != 0) {
			details = details + " You have selected " + countAccountHasInTeam + " accounts already in the team.";
		}

		noti.put("details", details);

		noti.put("timestamp", timestamp);
		return noti;
	}

	/*
	 * @see com.vti.services.TeamMemberService#deleteAccountFromTeam(java.util.List,
	 * java.util.List)
	 */
	@Override
	public Map<String, String> deleteAccountFromTeam(List<String> listIdTeamString, List<String> listIdAccountString) {
		// Check listIdTeam only have one element
		if (listIdTeamString.size() != 1) {
			throw new DataException("idTeam Error", "One and Only one idTeam in List");
		}

		// Check listIdAccount has at least one element
		if (listIdAccountString.size() == 0) {
			throw new DataException("listIdAccount Error", "Must have more one element in listIdAccount");
		}

		// Convert list string id team to list long
		List<Long> listIdTeam = new ArrayList<>();
		try {
			listIdTeam = listIdTeamString.stream().map(Long::parseLong).collect(Collectors.toList());
		} catch (Exception e) {
			throw new DataException("idTeam Error", "idTeam can't be character or null");
		}

		// Convert list string id account to list long
		List<Long> listIdAccount = new ArrayList<>();
		try {
			listIdAccount = listIdAccountString.stream().map(Long::parseLong).collect(Collectors.toList());
		} catch (Exception e) {
			throw new DataException("idAccount Error", "idAccount can't be character or null");
		}

		// Check Team exist
		Team checkTeam = teamService.findTeamById(listIdTeam.get(0));
		if (checkTeam == null) {
			throw new DataException("Team", "Can't find this team");
		}

		// Get list idAccount of this team at now by team repo
		List<Long> listIdAccountInTeamNow = teamMemberRepo.findAllTeamMemberByTeamID(checkTeam.getIdTeam()).stream()
				.map(s -> s.getIdAccount()).collect(Collectors.toList());

		int countAccountDeleted = 0;
		int countAccountNotExist = 0;
		int countAccountNotInTeam = 0;
		int countAccountIsLeader = 0;
		// Delete account from team member
		for (int i = 0; i < listIdAccount.size(); ++i) {
			// find account from id
			Account account = accountServices.findAccountById(listIdAccount.get(i));

			// if account not null and listAccountInTeam at now have that account and that
			// account is not leader(index != 0, index = -1 if not found => index > 0) =>
			// delete it and count++
			if (account != null) {
				// get index of account in this team
				int index = listIdAccountInTeamNow.indexOf(account.getIdAccount());
				if (index > -1) {
					if (index > 0) {
						// delete it from database
						teamMemberRepo.deleteMember(checkTeam, account);

						countAccountDeleted++;
					} else {
						// index = 0 -> that account is leader
						countAccountIsLeader++;
					}
				} else {
					// index = -1 -> can't find that account in this team
					countAccountNotInTeam++;
				}
			} else {
				// account is null -> not exist
				countAccountNotExist++;
			}
		}

		// Create noti
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String timestamp = dateFormat.format(new Date());

		Map<String, String> noti = new TreeMap<String, String>();
		// Warning or success
		if (countAccountDeleted != listIdAccount.size()) {
			noti.put("message", "WARNING");
		} else {
			noti.put("message", "SUCCESS");
		}

		// Create details message
		String details = "System has been deleted " + countAccountDeleted + "/" + listIdAccount.size()
				+ " account from " + checkTeam.getTeamName() + " team.";

		if (countAccountNotExist > 0) {
			details = details + " You have selected " + countAccountNotExist + " accounts that don't exist.";
		}

		if (countAccountNotInTeam > 0) {
			details = details + " You have selected " + countAccountNotInTeam + " accounts that are not in the team.";
		}
		
		if (countAccountIsLeader > 0) {
			details = details + " You have selected " + countAccountIsLeader + " leader account.";
		}

		noti.put("details", details);
		noti.put("timestamp", timestamp);
		
		return noti;
	}

}
