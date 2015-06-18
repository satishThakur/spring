package com.satish.app.sandbox;

import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class BucketObjectPrinter implements Consumer<S3ObjectSummary>{
	
	private static Logger logger = Logger.getLogger(BucketObjectPrinter.class);
	@Override
	public void accept(S3ObjectSummary s3ObjectSummary) {
		logger.info("Object key: " + s3ObjectSummary.getKey() + " last Mod: " + s3ObjectSummary.getLastModified());
		
	}

}
