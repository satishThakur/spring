package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.satish.app.common.dao.S3BucketDao;
import com.satish.app.common.dao.S3BucketHistoryDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.EC2Instance;
import com.satish.app.domain.Ec2InstanceHistory;
import com.satish.app.domain.S3Bucket;
import com.satish.app.domain.S3BucketHistory;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;
import com.satish.app.services.S3Service;
import com.satish.app.services.SyncStatusService;

@Service(value="S3Service")
public class S3BucketServiceImpl implements S3Service {

	
	@Autowired
	private S3BucketDao s3BucketDao;
	
	@Autowired
	private SyncStatusService syncStatsService;
	
	@Autowired
	private S3BucketHistoryDao s3HistoryDao;

	@Override
	public Collection<S3Bucket> getAllS3Buckets() {
		return s3BucketDao.findAll();
	}
	
	@Override
	public Collection<S3Bucket> getFilteredBuckets(Filter<InstanceInfo> infoFilter) {
		Collection<S3Bucket> allBuckets = getAllS3Buckets();
		Collection<S3Bucket> filteredBuckets = new ArrayList<>();
		
		for(S3Bucket bucket : allBuckets){
			InstanceInfo info = InstanceInfo.getInstanceInfo(bucket);
			if(infoFilter.isAllowed(info)){
				filteredBuckets.add(bucket);
			}
		}
		
		return filteredBuckets;
	}
	
	
	@Override
	public RegionStats getS3BucketCurrentStats() {
		List<S3Bucket> allInstances = getAllCurrentInstances();

		Map<String, Integer> regionCount = new HashMap<String, Integer>();
		String region = Region.getRegion(Regions.US_WEST_2).getName();
		regionCount.put(region, allInstances.size());
		return new RegionStats(regionCount);
	}

	
	private List<S3Bucket> getAllCurrentInstances(){
		Date successData  = syncStatsService.getLastSuccessDate();
		List<String> bucketNames = getAllBucketNamesOnDate(successData);
		return s3BucketDao.getInstancesByBucketNames(bucketNames);
	}

	private List<String> getAllBucketNamesOnDate(Date date){
		List<S3BucketHistory> allBuckets = s3HistoryDao.getAllInstancesOnDate(date);
		List<String> bucketNames = new ArrayList<String>();
		for(S3BucketHistory bucket : allBuckets){
			bucketNames.add(bucket.getBucketName());
		}
		return bucketNames;
	}
	
	
	@Override
	public Map<String,Integer> getAllClientsStats(){
		List<S3Bucket> allBuckets = getAllCurrentInstances();
		Map<String, Integer> clientsCount = new HashMap<String, Integer>();

		for(S3Bucket bucket : allBuckets){
			String client = bucket.getClient();
			if(clientsCount.containsKey(client)){
				clientsCount.put(client, clientsCount.get(client) + 1);
			}else{
				clientsCount.put(client, 1);
			}
		}
		return clientsCount;
	}

}
