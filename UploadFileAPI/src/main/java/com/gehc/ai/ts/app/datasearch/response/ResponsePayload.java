package com.gehc.ai.ts.app.datasearch.response;

/*
 * ResponsePayload.java
 *
 * Copyright (c) 2019 by General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponsePayload {
	private String uploadId;
	private Object files;
	private boolean status;
}
