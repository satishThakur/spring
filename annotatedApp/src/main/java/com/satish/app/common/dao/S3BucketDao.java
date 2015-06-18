package com.satish.app.common.dao;


import com.satish.app.common.hibernate.dao.HibernateDao;

import com.satish.app.domain.S3Bucket;

public interface S3BucketDao extends HibernateDao<S3Bucket, Long> {

	public S3Bucket getBucketByBucketName(String bucketName);

}
