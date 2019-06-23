package com.lnt.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
	String uploadFileToS3Bucket(MultipartFile multipartFile) throws Exception;

	String deleteFileFromS3Bucket(String fileUrl);
}
