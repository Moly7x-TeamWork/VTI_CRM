//
package com.vti.servicesImp;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vti.exception.DataException;
import com.vti.services.FileService;

@Service
public class FileServiceIMP implements FileService {

	/*
	 * @see
	 * com.vti.services.FileService#pasteNewFile(org.springframework.web.multipart.
	 * MultipartFile)
	 */
	@Override
	public Path pasteNewFile(MultipartFile file, String folderName) {
		// get fileName
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// create path
		Path path = Paths.get(System.getProperty("user.dir"), folderName);

		// delete all old file in fileCSV
//		try {
//			FileUtils.cleanDirectory(path.toFile());
//		} catch (Exception e1) {
//			throw new DataException("Path Error", "Dir to save CSV file has a problem");
//		}

		// Paste new fileCSV
		try {
			Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new DataException("Error", "Can't paste file to Path");
		}

		// return path to file
		return Paths.get(System.getProperty("user.dir"), folderName, fileName);
	}

	/*
	 * @see com.vti.services.FileService#DetectDelimiter(java.nio.file.Path)
	 */
	@Override
	public String DetectDelimiter(Path pathToFile) {
		// assume one of following delimiters
		List<String> possibleDelimiters = Arrays.asList(new String[] { ",", ";", "|" });

		try {
			BufferedReader reader = Files.newBufferedReader(pathToFile);
			String header = reader.readLine();

			for (String s : possibleDelimiters) {
				if (header.contains(s)) {
					return s;
				}
			}
		} catch (Exception e) {
			throw new DataException("Error", "Can't open file from Path");
		}
		return "\t";
	}

}
