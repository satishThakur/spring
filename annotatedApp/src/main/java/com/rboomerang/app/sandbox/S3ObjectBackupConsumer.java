package com.rboomerang.app.sandbox;

import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3ObjectBackupConsumer implements Consumer<S3ObjectSummary>{
	
	private static Logger logger = Logger.getLogger(S3ObjectBackupConsumer.class);

	
	private S3BucketServiceImpl s3BucketService;
	
	private String targetKeyPrefix;
	
	private String targetBucket;
	
	public S3ObjectBackupConsumer(S3BucketServiceImpl service,
			String targetBucket, String targetKeyPrefix){
		s3BucketService = service;
		this.targetBucket = targetBucket;
		this.targetKeyPrefix = targetKeyPrefix;
	}

	@Override
	public void accept(S3ObjectSummary s3ObjectSummary) {
		String targetKey = targetKeyPrefix + s3ObjectSummary.getKey();
		logger.info("Copying to bucket: " + targetBucket + " key: " + targetKey);
		boolean copySuccess = s3BucketService.copyTo(s3ObjectSummary.getKey(), targetBucket, targetKey);
		
		if(copySuccess){
			s3BucketService.delete(s3ObjectSummary.getKey());
		}else{
			logger.info("Copying to bucket: " + targetBucket + " key: " + targetKey + "Failed!!!");
		}
	}

}
