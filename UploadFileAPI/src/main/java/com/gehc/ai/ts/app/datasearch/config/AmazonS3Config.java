package com.gehc.ai.ts.app.datasearch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {
	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Config.class);
	@Value("${aws.s3.bucket.region}")
	private String awsS3BucketRegion;

	@Bean
	public AmazonS3 s3client() {
		LOGGER.info("Entering into s3client method {}", System.currentTimeMillis());
		try {
			return AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
			.withRegion(awsS3BucketRegion).build(); 
			/*
			 * return AmazonS3ClientBuilder.standard().withCredentials(new
			 * ProfileCredentialsProvider()) .withRegion(awsS3BucketRegion).build();
			 */
		} finally {
			LOGGER.info("Exist on s3client method at {}", System.currentTimeMillis());
		}
	}
}
