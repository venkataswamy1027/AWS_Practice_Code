package com.lnt.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lnt.service.AmazonS3Service;
import com.lnt.util.Utility;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
	private static final Logger logger = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

	@Value("${aws.s3.bucket.name}")
	private String awsS3BucketName;

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	private Utility utility;

	@Override
	public void uploadFileToS3Bucket(MultipartFile multipartFile) throws Exception {
		logger.info("Entering into uploadFileToS3Bucket method {}", System.currentTimeMillis());
		try {
			File file = utility.convertMultiPartToFile(multipartFile);
			String fileName = utility.generateFileName(multipartFile);
			logger.info("fileName :{}", fileName);
			utility.checkFileExtension(file);
			uploadFileTos3bucket(fileName, file);
		} catch (Exception e) {
			logger.info("file :{}", e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		logger.info("Entering into uploadFileTos3bucket method {}", System.currentTimeMillis());
		s3client.putObject(new PutObjectRequest(awsS3BucketName, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));

	}

	@Override
	public void deleteFileFromS3Bucket(String fileName) {
		logger.info("fileName :{}", fileName);
		File file = new File(fileName);
		utility.checkFileExtension(file);
		s3client.deleteObject(new DeleteObjectRequest(awsS3BucketName, fileName));
	}

}
