package com.lnt.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		String objectUrl = null;
		try {
			logger.info("multipartFile :{}", multipartFile);
			response = new HashMap<>();
			objectUrl = amazonS3Service.uploadFileToS3Bucket(multipartFile);
			response.put("message", objectUrl);
		} catch (Exception e) {
			logger.error("file :{}", e.getMessage());
			response.put("message", e.getMessage());
		}
		return response;
	}

	@DeleteMapping("/deleteFile")
	public Map<String, String> deleteFile(@RequestPart(value = "url") String fileUrl) {
		Map<String, String> response = null;
		String message = null;
		try {
			logger.info("fileUrl :{}", fileUrl);
			response = new HashMap<>();
			message = amazonS3Service.deleteFileFromS3Bucket(fileUrl);
			response.put("message", message);
		} catch (Exception e) {
			logger.error("file :{}", e.getMessage());
			response.put("message", e.getMessage());
		}
		return response;
	}

}
