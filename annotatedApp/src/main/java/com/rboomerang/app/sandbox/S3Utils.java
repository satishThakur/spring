package com.rboomerang.app.sandbox;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Utils {

	private static Logger logger = Logger.getLogger(S3Utils.class);

	public static void printFilesOlderThan(String bucket, Date olderThan){
		AmazonS3 s3Client = new AmazonS3Client();
		s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));

		getAllObjectSummaries(s3Client, bucket, 
				t -> t.getLastModified().compareTo(olderThan) <=0, 
				System.out::println);		

	}

	public static boolean copy(AmazonS3 s3Client, String sourceBucketName, 
			String sourceKey, String destinationBucketName,
			String destinationKey){

		boolean fileCopied = false;
		try{
			s3Client.copyObject(sourceBucketName, sourceKey, 
					destinationBucketName, destinationKey);
			fileCopied = true;
		}
		catch(Exception ex){
			logger.error("Copy file failed", ex);
		}

		return fileCopied;

	}
/*
	public static boolean deleteFile(AmazonS3 s3Client, String sourceBucketName,String sourceKey){
		boolean fileDeleted = false;
		try{
			s3Client.deleteObject(sourceBucketName, sourceKey);
			fileDeleted = true;
		}catch(Exception ex){
			logger.error("File delete failed", ex);
		}

		return fileDeleted;
	}
	
	*/


	private static void getAllObjectSummaries(AmazonS3 s3Client, String bucket, 
			Predicate<S3ObjectSummary> predicate,Consumer<S3ObjectSummary> consumer){
		ObjectListing objectListing = s3Client.listObjects(bucket);
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
				consumer.accept(summary);
			}
		});
	}


	public static void main(String[] args){
		System.out.println(System.getenv("CATALINA_HOME"));
		Calendar calender = Calendar.getInstance();
		calender.set(2015, Month.APRIL.getValue(), 1);
		Date date = calender.getTime();

		printFilesOlderThan("automatcher-qa", date);

	}


}
