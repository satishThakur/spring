package com.satish.app.sandbox;

import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class S3BucketBackupClient {
	
	
	public static void main(String[] args){
		
		Region region = Region.getRegion(Regions.US_WEST_2);
		String sourceBucket = "boomerangsearsperffiles";
		String destinationBucket = "boomerangsftpbackup";
		
		String destinationKeyPrefix = "sears_backup/";
		
		Calendar calender = Calendar.getInstance();
		calender.set(2015, 5, 18, 12, 0, 0);
		
		
		Date cutoffDate = calender.getTime();
		
		System.out.println(cutoffDate);
		
		//BucketObjectPredicate predicate = new BucketObjectPredicate(
		//		Arrays.asList("input", "output"), cutoffDate);
		
		//BucketObjectPrinter printer = new BucketObjectPrinter();
		
		AllFilesPredicate allFilesPredicate = new AllFilesPredicate();
		
		
		S3BucketServiceImpl service = new S3BucketServiceImpl(sourceBucket, region);
		
		S3CopyConsumer copyConsumer = new S3CopyConsumer(service, destinationBucket, destinationKeyPrefix);
		
		
		//S3ObjectBackupConsumer copier = new S3ObjectBackupConsumer(service, destinationBucket, destinationKeyPrefix);
		
		service.filter(allFilesPredicate, copyConsumer);
		
	}

}