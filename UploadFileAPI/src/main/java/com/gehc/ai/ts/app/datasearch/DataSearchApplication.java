package com.gehc.ai.ts.app.datasearch;

/*
 * DataSearchApplication.java
 *
 * Copyright (c) 2019 by General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataSearchApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSearchApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Entering into main method at {}", System.currentTimeMillis());
		SpringApplication.run(DataSearchApplication.class, args);
	}

}
