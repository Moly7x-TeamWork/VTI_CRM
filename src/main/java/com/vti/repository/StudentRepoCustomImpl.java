//
package com.vti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vti.dto.StudentDTO;

@Repository
@Transactional
public class StudentRepoCustomImpl implements StudentRepoCustom {

	@Autowired
	private EntityManager entityManager;

	/*
	 * @see
	 * com.vti.repository.StudentRepoCustom#saveAllStudentFromExcel(java.util.List,
	 * java.util.List)
	 */
	@Override
	public int saveAllStudentFromExcel(List<StudentDTO> students, List<String> phoneNumberExist) {
		String values = "";

		for (int i = 0; i < students.size(); ++i) {
			if (!(phoneNumberExist.contains(students.get(i).getPhoneNumber().replace("'", "")))) {
				values = values + students.get(i).toString();
			}
		}
		
		if(values.isEmpty()) {
			return 0;
		}
		
		String insertSQL = "INSERT INTO studentInfo(phoneNumber, idSource, firstname, lastname, gender, email, birthDate, school, address, social, idTarget, idStatus, transHistory) VALUES "
				+ values.substring(1);
		
		int count = entityManager.createNativeQuery(insertSQL).executeUpdate();
		entityManager.flush();
		entityManager.clear();

		return count;
	}

}
