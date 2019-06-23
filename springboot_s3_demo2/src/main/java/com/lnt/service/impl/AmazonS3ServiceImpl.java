package com.lnt.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lnt.service.AmazonS3Service;
import com.lnt.util.Utility;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
	private static final Logger logger = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

	@Value("${aws.s3.bucket.name}")
	private String awsS3BucketName;

	@Value("${aws.s3.document.directory.name}")
	private String s3DirectoryName;

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	private Utility utility;

	@Override
	public String uploadFileToS3Bucket(MultipartFile multipartFile) throws Exception {
		logger.info("Entering into uploadFileToS3Bucket method {}", System.currentTimeMillis());
		try {
			File file = utility.convertMultiPartToFile(multipartFile);
			String fileName = utility.generateFileName(multipartFile);
			logger.info("fileName :{}", fileName);
			utility.checkFileExtension(file);
			String objectUrl = uploadFileTos3bucket(fileName, file);
			logger.info("objectUrl :{}", objectUrl);
			return objectUrl;
		} catch (Exception e) {
			logger.info("file :{}", e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private String uploadFileTos3bucket(String fileName, File file) {
		logger.info("Entering into uploadFileTos3bucket method {}", System.currentTimeMillis());
		final String key = (s3DirectoryName + "/" + fileName).toLowerCase();
		logger.info("key :{}", key);
		s3client.putObject(
				new PutObjectRequest(awsS3BucketName, key, file).withCannedAcl(CannedAccessControlList.PublicRead));
		return ((AmazonS3Client) s3client).getResourceUrl(awsS3BucketName, key);
	}

	@Override
	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		logger.info("fileName :{}", fileName);
		File file = new File(fileName);
		utility.checkFileExtension(file);
		s3client.deleteObject(new DeleteObjectRequest(awsS3BucketName, fileName));
		return "Successfully deleted";
	}

}
