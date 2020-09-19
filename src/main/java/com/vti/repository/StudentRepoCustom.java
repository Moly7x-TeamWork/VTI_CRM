//
package com.vti.repository;

import java.util.List;

import com.vti.dto.StudentDTO;

public interface StudentRepoCustom {
	int saveAllStudentFromExcel(List<StudentDTO> students, List<String> phoneNumberExist);
}
