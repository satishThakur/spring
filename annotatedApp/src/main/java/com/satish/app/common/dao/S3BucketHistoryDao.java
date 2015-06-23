package com.satish.app.common.dao;

import java.util.Date;
import java.util.List;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.S3BucketHistory;

public interface S3BucketHistoryDao extends HibernateDao<S3BucketHistory, Long>{
	
	public S3BucketHistory getInstanceHistoryByDateAndName(Date date, String bucketName);

	public List<S3BucketHistory> getAllInstancesOnDate(Date date);
}
