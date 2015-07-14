package com.rboomerang.app.common.dao;

import java.util.Date;
import java.util.List;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.S3BucketHistory;

public interface S3BucketHistoryDao extends HibernateDao<S3BucketHistory, Long>{
	
	public S3BucketHistory getInstanceHistoryByDateAndName(Date date, String bucketName);

	public List<S3BucketHistory> getAllInstancesOnDate(Date date);
}
