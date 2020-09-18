//
package com.vti.services;

import java.util.List;
import java.util.Optional;

import com.vti.dto.StudentDTO;
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
	
	StudentDTO createStudent(StudentDTO studentDTO);
	
	Optional<Student> findStudentById(String phoneNumber);
	
	List<String> findStudentByListPhoneNumber(List<String> phoneNumber);
}
