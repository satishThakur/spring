package com.satish.app.sandbox;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;

public class S3DeleteBucketUtil {
	
	
	
	public static void deleteBucket(String bucketName){
		AmazonS3 s3Client = new AmazonS3Client();
		s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
		
		ObjectListing objectListing = s3Client.listObjects(bucketName);
		processObjectListing(s3Client, objectListing,bucketName);
		while(objectListing.isTruncated()){
			objectListing = s3Client.listNextBatchOfObjects(objectListing);

			processObjectListing(s3Client, objectListing,bucketName);
		}	
	}

	private static void processObjectListing(AmazonS3 s3Client,
			ObjectListing objectListing, String bucketName) {
		
		objectListing.getObjectSummaries().forEach(summaryObj -> {
			s3Client.deleteObject(bucketName, summaryObj.getKey());
		});
		
		
		
	}
	
	public static void main(String[] args){
		deleteBucket("satish-test-backup");
	}

}
