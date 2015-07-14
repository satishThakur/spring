package com.rboomerang.app.common.dao;


import java.util.List;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.S3Bucket;

public interface S3BucketDao extends HibernateDao<S3Bucket, Long> {

	public S3Bucket getBucketByBucketName(String bucketName);

	public List<S3Bucket> getInstancesByBucketNames(List<String> bucketNames);

}
