package com.rboomerang.app.sandbox;

import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3ObjectDeleteConsumer implements Consumer<S3ObjectSummary>{

	private static Logger logger = Logger.getLogger(S3ObjectBackupConsumer.class);


	private S3BucketServiceImpl s3BucketService;

	public S3ObjectDeleteConsumer(S3BucketServiceImpl service){
		s3BucketService = service;
	}

	@Override
	public void accept(S3ObjectSummary s3ObjectSummary) {

		logger.info("Deleting : key: " + s3ObjectSummary.getKey());


		boolean isDeleted = s3BucketService.delete(s3ObjectSummary.getKey());
		if(!isDeleted){
			logger.info("Deleting key: "  + s3ObjectSummary.getKey() + "Failed!!!");
		}
	}

}
