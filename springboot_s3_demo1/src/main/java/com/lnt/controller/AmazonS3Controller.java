package com.lnt.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lnt.service.AmazonS3Service;

@RestController
public class AmazonS3Controller {

	private static final Logger logger = LoggerFactory.getLogger(AmazonS3Controller.class);

	@Autowired
	private AmazonS3Service amazonS3Service;

	@PostMapping("/uploadFile")
	public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
		logger.info("Entering into uploadFile method {}", System.currentTimeMillis());
		Map<String, String> response = null;
		try {
			logger.info("multipartFile :{}", multipartFile);
			response = new HashMap<>();
			amazonS3Service.uploadFileToS3Bucket(multipartFile);
			response.put("message",
					"file [" + multipartFile.getOriginalFilename() + "] uploading request submitted successfully.");
			return response;
		} catch (Exception e) {
			logger.error("file :{}", e.getMessage());
			response.put("message", e.getMessage());
		}
		return response;
	}

	@DeleteMapping("/deleteFile")
	public Map<String, String> deleteFile(@RequestParam("file_name") String fileName) {
		Map<String, String> response = null;
		try {
			logger.info("fileName :{}", fileName);
			response = new HashMap<>();
			amazonS3Service.deleteFileFromS3Bucket(fileName);
			response.put("message", "file [" + fileName + "] removing request submitted successfully.");
			return response;
		} catch (Exception e) {
			logger.error("file :{}", e.getMessage());
			response.put("message", e.getMessage());
		}
		return response;
	}

}
