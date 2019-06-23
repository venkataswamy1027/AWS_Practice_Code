package com.gehc.ai.ts.app.datasearch.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.fasterxml.uuid.Generators;
import com.gehc.ai.ts.app.datasearch.constant.Constants;
import com.gehc.ai.ts.app.datasearch.request.UploadFileRequest;
import com.gehc.ai.ts.app.datasearch.response.FileResponse;
import com.gehc.ai.ts.app.datasearch.response.ResponsePayload;
import com.gehc.ai.ts.app.datasearch.service.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	@Value("${aws.s3.bucket.name}")
	private String awsS3BucketName;

	@Autowired
	private AmazonS3 s3Client;

	@Override
	public ResponseEntity<ResponsePayload> generatePresignedURLFromS3Bucket(UploadFileRequest uploadFileRequest) {
		LOGGER.info("Entering into generatePresignedURLFromS3Bucket method at {}", System.currentTimeMillis());
		ResponsePayload responsePayload;
		List<FileResponse> fileResponseList;
		GeneratePresignedUrlRequest generatePresignedUrlRequest;
		Date expiration;
		UUID uuid;
		try {
			fileResponseList = new ArrayList<>();
			/*
			 * Set The amount of time that the PRE-signed URL is valid, The PRE-signed URL
			 * is expire after one hour.
			 */
			expiration = new Date();
			long expTimeMillis = expiration.getTime();
			expTimeMillis += 1000 * 60 * 60;
			expiration.setTime(expTimeMillis);
			// Generate time-based UUID
			uuid = Generators.timeBasedGenerator().generate();
			LOGGER.info("UUID {} ", uuid);
			// Generate the PRE-signed URL.
			for (String fileName : uploadFileRequest.getFiles()) {
				FileResponse fr = new FileResponse();
				generatePresignedUrlRequest = new GeneratePresignedUrlRequest(awsS3BucketName + Constants.SUFFIX + uuid,
						fileName).withMethod(HttpMethod.PUT).withExpiration(expiration);
				URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
				fr.setName(fileName);
				fr.setUrl(url);
				LOGGER.info("fr {}", fr);
				fileResponseList.add(fr);
			}
			responsePayload = new ResponsePayload();
			responsePayload.setUploadId(uuid.toString());
			responsePayload.setFiles(fileResponseList);
			responsePayload.setStatus(true);
			return new ResponseEntity<>(responsePayload, HttpStatus.OK);
		} finally {
			LOGGER.info("Exiting on generatePresignedURLFromS3Bucket method at {}", System.currentTimeMillis());
		}
	}
}
