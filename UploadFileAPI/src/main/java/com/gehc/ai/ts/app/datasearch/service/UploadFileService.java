package com.gehc.ai.ts.app.datasearch.service;

import org.springframework.http.ResponseEntity;

import com.gehc.ai.ts.app.datasearch.request.UploadFileRequest;
import com.gehc.ai.ts.app.datasearch.response.ResponsePayload;

public interface UploadFileService {

	ResponseEntity<ResponsePayload> generatePresignedURLFromS3Bucket(UploadFileRequest uploadFileRequest);
}
