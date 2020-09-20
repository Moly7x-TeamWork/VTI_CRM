//
package com.vti.servicesImp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vti.dto.StudentDTO;
import com.vti.entity.Gender;
import com.vti.entity.Student;
import com.vti.exception.DataException;
import com.vti.repository.StudentRepo;
import com.vti.services.FileService;
import com.vti.services.SourceService;
import com.vti.services.StatusService;
import com.vti.services.StudentService;
import com.vti.services.TargetService;

@Service
public class StudentServiceIMP implements StudentService {

	@Autowired
	StudentRepo studentRepo;

	@Autowired
	SourceService sourceService;

	@Autowired
	TargetService targetService;

	@Autowired
	StatusService statusService;

	@Autowired
	FileService fileService;

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

		if (!checkStudent.isPresent()) {
			throw new DataException("Duplicate PhoneNumber", "This PhoneNumber has been used.");
		}

		// if not save it
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
		// create new student
		Student student = new Student();
		student.setPhoneNumber(studentDTO.getPhoneNumber());
		student.setSource(sourceService.findBySourceDetail(studentDTO.getSource()));
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setGender(Gender.valueOf(studentDTO.getGender()));
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

		// save it
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
	 * @see com.vti.services.StudentService#findStudentByListPhoneNumber(java.lang.
	 * String)
	 */
	@Override
	public List<String> findStudentByListPhoneNumber(List<String> phoneNumber) {
		return studentRepo.filterToListPhoneNumberExist(phoneNumber);
	}

	/*
	 * @see
	 * com.vti.services.StudentService#importStudentByCSV(org.springframework.web.
	 * multipart.MultipartFile)
	 */
	@Override
	public Map<String, String> importStudentByFile(MultipartFile file) {

		// create path
		Path path = Paths.get(System.getProperty("user.dir"), "exampleTemplate");

		// Create Map Source
		Map<String, String> sourceMap = createMapSourceTargetStatus(path, "source.xlsx");

		// Create Map Target
		Map<String, String> targetMap = createMapSourceTargetStatus(path, "target.xlsx");

		// Create Map Status
		Map<String, String> statusMap = createMapSourceTargetStatus(path, "status.xlsx");

		try {
			// Getting a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(file.getInputStream());

			// Paste file to fileExcel if file can open without password
			fileService.pasteNewFile(file, "fileExcel");

			// Getting the Sheet at index zero
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.rowIterator();

			// check header and number of column
			int sizeRow = rowIterator.next().getLastCellNum() - 1;

			if (sizeRow != 12) {
				throw new DataException("Column", "Number of Columns is not right. It must has 13 columns");
			}

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			// List studentDTO
			List<StudentDTO> students = new ArrayList<StudentDTO>();

			// number of raw record
			int countRecord = 0;
			
			// number of inserted
			int countInserted = 0;

			// loop each row
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// phoneNumber can't be empty
				if (!(dataFormatter.formatCellValue(row.getCell(0)).isEmpty())) {
					countRecord++;

					// create tempStudent
					StudentDTO tempStudent = createTempStudent(dataFormatter, row, sourceMap, targetMap, statusMap);

					students.add(tempStudent);
				}
			}

			// Close workbook
			workbook.close();

			// filter duplicate phoneNumber in record
			students = students.stream().distinct().collect(Collectors.toList());

			// Get list phoneNumber in list student
			List<String> phoneNumberExist = students.stream().map(s -> s.getPhoneNumber().replace("'", ""))
					.collect(Collectors.toList());

			// Filter phoneNumber to phoneNumber exist in database
			phoneNumberExist = studentRepo.filterToListPhoneNumberExist(phoneNumberExist);

			countInserted = studentRepo.saveAllStudentFromExcel(students, phoneNumberExist);
			
			// Create noti
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String timestamp = dateFormat.format(new Date());

			Map<String, String> noti = new LinkedHashMap<String, String>();

			// Warning or success
			if (countInserted != countRecord) {
				noti.put("message", "WARNING");
			} else {
				noti.put("message", "SUCCESS");
			}

			// create details message
			String details = "System been added " + countInserted + "/" + countRecord + " student to database.";

			if (countInserted != countRecord) {
				details = details + " There are " + (countRecord - countInserted) + " phone numbers duplicate.";
			}

			noti.put("details", details);

			noti.put("timestamp", timestamp);

			return noti;
		} catch (EncryptedDocumentException e) {
			throw new DataException("File Password",
					file.getOriginalFilename() + " has password. You must be unlock this file first");
		} catch (IOException e) {
			throw new DataException("File Input", file.getOriginalFilename() + " is wrong format. Must be .xlsx or .xls");
		}

	}

	private StudentDTO createTempStudent(DataFormatter dataFormatter, Row row, Map<String, String> sourceMap,
			Map<String, String> targetMap, Map<String, String> statusMap) {

		StudentDTO tempStudent = new StudentDTO();

		tempStudent.setPhoneNumber("'" + dataFormatter.formatCellValue(row.getCell(0)).trim() + "'");
		// Get source id from detail in map
		tempStudent.setSource(sourceMap.get(dataFormatter.formatCellValue(row.getCell(1)).toLowerCase()));

		if (dataFormatter.formatCellValue(row.getCell(2)).isEmpty()) {
			tempStudent.setFirstName(null);
		} else
			tempStudent.setFirstName("'" + dataFormatter.formatCellValue(row.getCell(2)).replace("'", "''").trim() + "'");

		if (dataFormatter.formatCellValue(row.getCell(3)).isEmpty()) {
			tempStudent.setLastName(null);
		} else
			tempStudent.setLastName("'" + dataFormatter.formatCellValue(row.getCell(3)).replace("'", "''").trim() + "'");

		try {
			Gender.valueOf(dataFormatter.formatCellValue(row.getCell(4)));
			tempStudent.setGender("'" + dataFormatter.formatCellValue(row.getCell(4)).replace("'", "''").trim() + "'");
		} catch (Exception e) {
			tempStudent.setGender(null);
		}

		if (dataFormatter.formatCellValue(row.getCell(5)).isEmpty()) {
			tempStudent.setEmail(null);
		} else
			tempStudent.setEmail("'" + dataFormatter.formatCellValue(row.getCell(5)).replace("'", "''").trim() + "'");

		try {
			tempStudent.setBirthDate(row.getCell(6).getDateCellValue());
		} catch (Exception e) {
			tempStudent.setBirthDate("null");
		}

		if (dataFormatter.formatCellValue(row.getCell(7)).isEmpty()) {
			tempStudent.setSchool(null);
		} else
			tempStudent.setSchool("'" + dataFormatter.formatCellValue(row.getCell(7)).replace("'", "''").trim() + "'");

		if (dataFormatter.formatCellValue(row.getCell(8)).isEmpty()) {
			tempStudent.setAddress(null);
		} else
			tempStudent.setAddress("'" + dataFormatter.formatCellValue(row.getCell(8)).replace("'", "''").trim() + "'");

		if (dataFormatter.formatCellValue(row.getCell(9)).isEmpty()) {
			tempStudent.setSocial(null);
		} else
			tempStudent.setSocial("'" + dataFormatter.formatCellValue(row.getCell(9)).replace("'", "''").trim() + "'");

		// Get target id from detail in map
		tempStudent.setTarget(targetMap.get(dataFormatter.formatCellValue(row.getCell(10)).toLowerCase()));

		// Get status id from detail in map
		tempStudent.setStatus(statusMap.get(dataFormatter.formatCellValue(row.getCell(11)).toLowerCase()));

		if (dataFormatter.formatCellValue(row.getCell(12)).isEmpty()) {
			tempStudent.setTransHistory(null);
		} else
			tempStudent.setTransHistory("'" + dataFormatter.formatCellValue(row.getCell(12)).replace("'", "''").trim() + "'");

		return tempStudent;
	}

	private Map<String, String> createMapSourceTargetStatus(Path path, String fileName) {

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();
		// Create Map Source
		Map<String, String> map = new HashMap<>();

		Workbook workbook = createWorkBook(path, fileName);

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// create Iterator row
		Iterator<Row> rowIterator = sheet.rowIterator();

		// check header and number of column
		int sizeRow = rowIterator.next().getLastCellNum();

		if (sizeRow != 2) {
			throw new DataException("Column", "Number of Columns is not right. It must has 2 columns");
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			// put in map <detail, id>
			map.put(dataFormatter.formatCellValue(row.getCell(1)).toLowerCase(),
					dataFormatter.formatCellValue(row.getCell(0)).toLowerCase());
		}

		// close workbook
		try {
			workbook.close();
		} catch (IOException e) {
			throw new DataException(fileName, "This file can't close");
		}

		return map;
	}

	private Workbook createWorkBook(Path path, String fileName) {
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		try {
			return WorkbookFactory.create(path.resolve(fileName).toFile());
		} catch (EncryptedDocumentException e) {
			throw new DataException("File Password", fileName + " has password. You must be unlock this file first");
		} catch (IOException e) {
			throw new DataException("File Format", fileName + " is wrong format. Must be .xlsx or .xls");
		}
	}
}
