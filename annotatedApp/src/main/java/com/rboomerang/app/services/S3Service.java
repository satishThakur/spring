package com.rboomerang.app.services;

import java.util.Collection;
import java.util.Map;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.S3Bucket;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.RegionStats;

public interface S3Service {

	public Collection<S3Bucket> getAllS3Buckets();
	
	public Collection<S3Bucket> getFilteredBuckets(Filter<InstanceInfo> info);

	public RegionStats getS3BucketCurrentStats();

	public Map<String, Integer> getAllClientsStats();


}
