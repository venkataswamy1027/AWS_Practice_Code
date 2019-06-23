package com.lnt.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Utility {
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);

	public File convertMultiPartToFile(MultipartFile file) throws IOException {
		logger.info("Entering into convertMultiPartToFile method {}", System.currentTimeMillis());
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		logger.info("convFile :{}", convFile);
		return convFile;
	}

	public String generateFileName(MultipartFile multiPart) {
		logger.info("Entering into generateFileName method {}", System.currentTimeMillis());
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	public void checkFileExtension(File file) throws RuntimeException {
		logger.info("Entering into checkFileExtension method {}", System.currentTimeMillis());
		try {
			String fileName = file.getName();
			logger.info("uploaded fileName :{}", fileName);
			if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				logger.info("uploaded file extension :{}", extension);
				if (!extension.toLowerCase().matches("png|jpg|jpeg|xls|pdf")) {
					throw new RuntimeException(
							"file format not supported (supported formats png, jpg, jpeg, xls and pdf)");
				}
			} else {
				throw new RuntimeException("file format unknown");
			}
		} finally {
			logger.info("Exiting on checkFileExtension method {}", System.currentTimeMillis());
		}
	}
}
