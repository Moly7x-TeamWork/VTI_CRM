//
package com.vti.services;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public Path pasteNewFile(MultipartFile file, String folderName);
	
	public String DetectDelimiter(Path pathToFile);
}
