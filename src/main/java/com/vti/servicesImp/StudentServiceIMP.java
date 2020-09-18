//
package com.vti.servicesImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.dto.StudentDTO;
import com.vti.entity.Student;
import com.vti.exception.DataException;
import com.vti.repository.StudentRepo;
import com.vti.services.SourceService;
import com.vti.services.StatusService;
import com.vti.services.StudentService;
import com.vti.services.TargetService;

@Service
public class StudentServiceIMP implements StudentService{
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	SourceService sourceService;
	
	@Autowired
	TargetService targetService;
	
	@Autowired
	StatusService statusService;
	
	/* 
	* @see com.vti.services.StudentService#findAllStudent()
	*/
	@Override
	public List<Student> findAllStudent() {
		return studentRepo.findAll();
	}

	/* 
	* @see com.vti.services.StudentService#createStudent(com.vti.entity.Student)
	*/
	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {
		// Check this student exist or not
		Optional<Student> checkStudent = studentRepo.findById(studentDTO.getPhoneNumber());
		
		if(!checkStudent.isPresent()) {
			throw new DataException("Duplicate PhoneNumber", "This PhoneNumber has been used.");
		}
		
		// if not save it
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");   
		//create new student
		Student student = new Student();
		student.setPhoneNumber(studentDTO.getPhoneNumber());
		student.setSource(sourceService.findBySourceDetail(studentDTO.getSource()));
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setGender(studentDTO.getGender());
		student.setEmail(student.getEmail());
		
		try {
			student.setBirthDate(format.parse(studentDTO.getBirthDate()));
		} catch (ParseException e) {
			throw new DataException("Invalid Birth Date", "BirthDate format is not right. Must be Day-Month-Year");
		}
		
		student.setSchool(studentDTO.getSchool());
		student.setAddress(studentDTO.getAddress());
		student.setSocial(studentDTO.getSocial());
		student.setTarget(targetService.findByTargetDetail(studentDTO.getTarget()));
		student.setStatus(statusService.findByStatusDetail(studentDTO.getStatus()));
		student.setTransHistory(studentDTO.getTransHistory());
		
		//save it
		studentRepo.saveAndFlush(student);
		return studentDTO;
	}

	/* 
	* @see com.vti.services.StudentService#findStudentById(java.lang.String)
	*/
	@Override
	public Optional<Student> findStudentById(String phoneNumber) {
		return studentRepo.findById(phoneNumber);
	}

	/* 
	* @see com.vti.services.StudentService#findStudentByListPhoneNumber(java.lang.String)
	*/
	@Override
	public List<String> findStudentByListPhoneNumber(List<String> phoneNumber) {
		return studentRepo.findStudentByListPhoneNumber(phoneNumber);
	}

}
