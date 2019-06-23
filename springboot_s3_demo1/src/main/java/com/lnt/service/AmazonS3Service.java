package com.lnt.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
	void  uploadFileToS3Bucket(MultipartFile multipartFile) throws Exception;

	void deleteFileFromS3Bucket(String fileName);
}
