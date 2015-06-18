package com.satish.app.sandbox;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3BucketServiceImpl {
	
	private static Logger logger = Logger.getLogger(S3Utils.class);
	
	private AmazonS3 s3Client;
	
	private String bucketName;
	
	public S3BucketServiceImpl(String bucketName,Region region){
		s3Client = new AmazonS3Client();
		this.bucketName = bucketName;
		s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
	}
	
	
	public void filter(Predicate<S3ObjectSummary> predicate,Consumer<S3ObjectSummary> consumer){
		ObjectListing objectListing = s3Client.listObjects(bucketName);
		processObjectListing(predicate, consumer, objectListing);
		while(objectListing.isTruncated()){
			objectListing = s3Client.listNextBatchOfObjects(objectListing);

			processObjectListing(predicate, consumer, objectListing);
		}	
	}
	
	
	private static void processObjectListing(
			Predicate<S3ObjectSummary> predicate,
			Consumer<S3ObjectSummary> consumer, ObjectListing objectListing) {
		objectListing.getObjectSummaries().forEach(summary ->{
			if(predicate.test(summary)){
				logger.info("Successful match " + summary.getKey() + " " + summary.getLastModified());
				consumer.accept(summary);
			}else{
				logger.info("NO Match!! " + summary.getKey() + " " + summary.getLastModified());
			}
		});
	}
	
	public boolean copyTo(String sourceKey, String targetBucker, String targetKey){
		boolean fileCopied = false;
		try{
			s3Client.copyObject(bucketName, sourceKey, 
					targetBucker, targetKey);
			fileCopied = true;
		}
		catch(Exception ex){
			logger.error("Copy file failed", ex);
		}

		return fileCopied;
	}
	
	public boolean delete(String key){
		boolean fileDeleted = false;
		try{
			s3Client.deleteObject(bucketName, key);
			logger.info("File deleted successfully bucket: " + bucketName + " key: " + key);
			fileDeleted = true;
		}catch(Exception ex){
			logger.error("File delete failed", ex);
		}

		return fileDeleted;
	}

}
