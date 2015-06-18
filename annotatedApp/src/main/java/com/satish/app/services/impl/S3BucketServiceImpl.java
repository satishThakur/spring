package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.common.dao.S3BucketDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.S3Bucket;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.S3Service;

@Service(value="S3Service")
public class S3BucketServiceImpl implements S3Service {

	
	@Autowired
	private S3BucketDao s3BucketDao;

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

}
