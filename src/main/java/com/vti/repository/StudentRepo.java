//
package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, String>{
	
	@Query("SELECT s.phoneNumber FROM Student s WHERE s.phoneNumber IN :listPhoneNumber")
	List<String> findStudentByListPhoneNumber(@Param("listPhoneNumber") List<String> phoneNumber);

}
