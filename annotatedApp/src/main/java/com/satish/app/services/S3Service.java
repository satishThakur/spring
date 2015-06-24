package com.satish.app.services;

import java.util.Collection;
import java.util.Map;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.S3Bucket;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;

public interface S3Service {

	public Collection<S3Bucket> getAllS3Buckets();
	
	public Collection<S3Bucket> getFilteredBuckets(Filter<InstanceInfo> info);

	public RegionStats getS3BucketCurrentStats();

	public Map<String, Integer> getAllClientsStats();


}
