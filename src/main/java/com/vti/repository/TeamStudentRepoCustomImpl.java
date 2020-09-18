//
package com.vti.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TeamStudentRepoCustomImpl implements TeamStudentRepoCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void customSaveAll(List<Map<String, String>> objects) {
		String values = "";
		for (int i = 0; i < objects.size(); ++i) {
			values = values + ",(" + objects.get(i).get("idTeam") + "," + objects.get(i).get("idAccount") + ","
					+ objects.get(i).get("phoneNumber") + ")";
		}
		values = values.substring(0, 0) + values.substring(1);

		String insertSQL = "INSERT INTO teamStudent (idTeam, idAccount, phoneNumber) VALUES " + values
				+ " ON DUPLICATE KEY UPDATE idTeam = values(idTeam), idAccount = values(idAccount), phoneNumber = values(phoneNumber)";

		entityManager.createNativeQuery(insertSQL).executeUpdate();
		entityManager.flush();
		entityManager.clear();
	}
}
