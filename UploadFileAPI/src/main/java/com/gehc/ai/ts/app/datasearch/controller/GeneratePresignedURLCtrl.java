package com.gehc.ai.ts.app.datasearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gehc.ai.ts.app.datasearch.request.UploadFileRequest;
import com.gehc.ai.ts.app.datasearch.response.ResponsePayload;
import com.gehc.ai.ts.app.datasearch.service.UploadFileService;

@RestController
@RequestMapping(value = "/api")
public class GeneratePresignedURLCtrl {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePresignedURLCtrl.class);

	@Autowired
	private UploadFileService uploadFileService;

	@PostMapping("/uploadFile")
	public ResponseEntity<ResponsePayload> generatePresignedURL(@RequestBody UploadFileRequest uploadFileRequest) {
		LOGGER.info("Entering into generatePresignedURL method at {}", System.currentTimeMillis());
		try {
			return uploadFileService.generatePresignedURLFromS3Bucket(uploadFileRequest);
		} finally {
			LOGGER.info("Exiting on generatePresignedURL method at {}", System.currentTimeMillis());
		}
	}
}
