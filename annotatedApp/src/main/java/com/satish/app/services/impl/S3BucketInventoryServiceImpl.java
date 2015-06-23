package com.satish.app.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.satish.app.common.dao.S3BucketDao;
import com.satish.app.common.dao.S3BucketHistoryDao;
import com.satish.app.domain.S3Bucket;
import com.satish.app.domain.S3BucketHistory;
import com.satish.app.services.S3InventorySyncService;
import com.satish.app.util.ConverterUtils;

@Service(value="S3InventorySyncService")
public class S3BucketInventoryServiceImpl implements S3InventorySyncService{
	private static Logger logger = Logger.getLogger(S3BucketInventoryServiceImpl.class);
	
	@Autowired
	private S3BucketDao s3BucketDao;
	
	@Autowired
	private S3BucketHistoryDao s3HistoryDao;
	
	@Override
	public void sync(Date syncDate){
		logger.info("================= Started Syncing S3Buckets ==================");
		AmazonS3 s3Client = new AmazonS3Client();
		List<Bucket> s3Buckets = s3Client.listBuckets();
		for(Bucket bucket : s3Buckets){
			long bucketSize=0;
			logger.debug("persisting: " + bucket.getName());
			bucketSize=0;
			S3Bucket  s3BucketDomain = ConverterUtils.convertS3Bucket(bucket,bucketSize);
			syncBucketInstance(s3BucketDomain, syncDate);
					
		}
		
		logger.info("================= Syncing S3Buckets Done==================");
		
	}
	
	private void syncBucketInstance(S3Bucket s3BucketDomain,Date syncDate){
		S3Bucket persisted = s3BucketDao.getBucketByBucketName(s3BucketDomain.getBucketName());
		if(persisted == null){
			logger.info("persisting bucket " + s3BucketDomain.getBucketName());
			s3BucketDao.makePersistent(s3BucketDomain);
		}else{
			logger.info("Already in db ignoring...");
			//in future might need to merge few states ??
		}
		
		S3BucketHistory historyInstance = s3HistoryDao.getInstanceHistoryByDateAndName(syncDate, s3BucketDomain.getBucketName());
		if(historyInstance == null){
			historyInstance = new S3BucketHistory(s3BucketDomain);
			historyInstance.setDate(syncDate);
			s3HistoryDao.makePersistent(historyInstance);
		}else{
			logger.info("s3bucket instance already present " + s3BucketDomain.getBucketName());
		}
	}
	
	@SuppressWarnings("unused")
	private  long s3BucketSizeCalculator(Bucket bucket,AmazonS3 s3Client)
	{
		    long totalSize = 0;
		    int totalItems = 0;
		    ObjectListing objects = s3Client.listObjects(bucket.getName());
		    do {
		        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
		            totalSize += objectSummary.getSize();
		            totalItems++;
		        }
		        objects = s3Client.listNextBatchOfObjects(objects);
		    } while (objects.isTruncated());
		    
		    totalSize=totalSize/1000000;
		    logger.debug("Bucketname: " + bucket.getName()+" with "+totalItems+"  is of size: "+totalSize+" Mb");
		    return totalSize;
	}
}
	
	

