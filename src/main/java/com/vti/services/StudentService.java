//
package com.vti.services;

import java.util.List;

import com.vti.dto.CreateStudentDTO;
import com.vti.entity.Student;

public interface StudentService {
	/**
	 * 
	 * This method is find all student
	 * 
	 * @author: Tân
	 * @create_date: 16/09/2020
	 * @return List of Student
	 */
	List<Student> findAllStudent();
	
	CreateStudentDTO createStudent(CreateStudentDTO studentDTO);
}
