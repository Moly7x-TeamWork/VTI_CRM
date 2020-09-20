//
package com.vti.servicesImp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.ITeamStudentDTO;
import com.vti.entity.Account;
import com.vti.entity.Team;
import com.vti.entity.TeamMember;
import com.vti.exception.DataException;
import com.vti.repository.TeamStudentRepo;
import com.vti.services.AccountServices;
import com.vti.services.StudentService;
import com.vti.services.TeamMemberService;
import com.vti.services.TeamService;
import com.vti.services.TeamStudentService;

@Service
public class TeamStudentServiceIMP implements TeamStudentService {

	@Autowired
	TeamStudentRepo teamStudentRepo;

	@Autowired
	private TeamService teamService;

	@Autowired
	private AccountServices accountServices;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeamMemberService teamMemberService;

	/*
	 * @see com.vti.services.TeamStudentService#findAllTeamStudent()
	 */
	@Override
	public List<ITeamStudentDTO> findAllTeamStudent() {
		return teamStudentRepo.findAllTeamStudent();
	}

	/*
	 * @see
	 * com.vti.services.TeamStudentService#addStudentToTeamAdviser(java.util.Map)
	 */
	@Override
	public Map<String, String> addStudentToTeamAdviser(List<String> listIdTeamString, List<String> idAccountString,
			List<String> listPhoneNumberStudent) {

		// Check Account or Team must be null
		if (!((listIdTeamString == null || listIdTeamString.isEmpty())
				^ (idAccountString == null || idAccountString.isEmpty()))) {
			throw new DataException("Input Error", "idTeam and idAccount. One of two must be Null or Empty");
		}

		Team checkTeam = null;
		// if idTeam not null
		if (!(listIdTeamString.isEmpty() || listIdTeamString == null)) {
			// Check IdTeam only have one element
			if (listIdTeamString.size() != 1) {
				throw new DataException("idTeam Error", "One and Only one idTeam in List");
			}

			// Convert list string id team to list long
			List<Long> listIdTeam = new ArrayList<>();
			try {
				listIdTeam = listIdTeamString.stream().map(Long::parseLong).collect(Collectors.toList());
			} catch (Exception e) {
				throw new DataException("idTeam Error", "idTeam can't be character or null");
			}

			// Check Team exist
			checkTeam = teamService.findTeamById(listIdTeam.get(0));
			if (checkTeam == null) {
				throw new DataException("Team", "Can't find this team");
			}
		}

		Account checkAccount = null;
		// if idAccount not null
		if (!(idAccountString.isEmpty() || idAccountString == null)) {
			// Check idAccount only have one element
			if (idAccountString.size() != 1) {
				throw new DataException("idAccount Error", "One and Only one idAccount in List");
			}

			// Convert list string id account to list long
			List<Long> listIdAccount = new ArrayList<>();
			try {
				listIdAccount = idAccountString.stream().map(Long::parseLong).collect(Collectors.toList());
			} catch (Exception e) {
				throw new DataException("idAccount Error", "idAccount can't be character or null");
			}

			// Check Account exist
			checkAccount = accountServices.findAccountById(listIdAccount.get(0));
			if (checkAccount == null) {
				throw new DataException("Account", "Can't find this account");
			}

			// Check Account not belong to any team
			Optional<List<TeamMember>> checkTeamMember = teamMemberService.findByAccount(checkAccount);
			if (checkTeamMember.isPresent()) {
				throw new DataException("Account", "Account must not belong to any team.");
			}

		}

		// Check listPhoneNumberStudent has at least one element
		if (listPhoneNumberStudent == null || listPhoneNumberStudent.size() == 0) {
			throw new DataException("listPhoneNumberStudent Error",
					"Must have more one element in listPhoneNumberStudent");
		}

		// Find all students exist
		List<String> students = studentService.findStudentByListPhoneNumber(listPhoneNumberStudent);
		int countStudentNotExist = listPhoneNumberStudent.size() - students.size();

		int countInserted = 0;
		// insert into teamStudent table
		List<Map<String, String>> input = new ArrayList<Map<String, String>>();

		// if idteam not null
		if (!(listIdTeamString.isEmpty() || listIdTeamString == null)) {
			for (int i = 0; i < students.size(); ++i) {
				input.add(new LinkedHashMap<String, String>());
				input.get(i).put("idTeam", Long.toString(checkTeam.getIdTeam()));
				input.get(i).put("idAccount", null);
				input.get(i).put("phoneNumber", students.get(i));

				// count
				++countInserted;
			}
			// save it
			teamStudentRepo.customSaveAll(input);
		}

		// if idAccount not null
		if (!(idAccountString.isEmpty() || idAccountString == null)) {
			for (int i = 0; i < students.size(); ++i) {
				input.add(new LinkedHashMap<String, String>());
				input.get(i).put("idTeam", null);
				input.get(i).put("idAccount", Long.toString(checkAccount.getIdAccount()));
				input.get(i).put("phoneNumber", students.get(i));

				// count
				++countInserted;
			}
			// save it
			teamStudentRepo.customSaveAll(input);
		}

		// Create Noti
		Map<String, String> noti = new LinkedHashMap<String, String>();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String timestamp = dateFormat.format(new Date());

		// Warning or success
		if (countStudentNotExist != 0) {
			noti.put("message", "WARNING");
		} else {
			noti.put("message", "SUCCESS");
		}

		// Create details message
		String details;
		if (!(listIdTeamString.isEmpty() || listIdTeamString == null)) {
			details = "System has inserted " + countInserted + "/" + listPhoneNumberStudent.size()
					+ " students for " + checkTeam.getTeamName() + " team.";
		} else {
			details = "System has inserted " + countInserted + "/" + listPhoneNumberStudent.size()
					+ " students for " + checkAccount.getEmail() + " account.";
		}

		if (countStudentNotExist > 0) {
			details = details + " You have selected " + countStudentNotExist + " students that don't exist.";
		}

		noti.put("details", details);
		noti.put("timestamp", timestamp);

		return noti;
	}

}
