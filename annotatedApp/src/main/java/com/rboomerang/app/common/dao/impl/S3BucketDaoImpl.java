package com.rboomerang.app.common.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rboomerang.app.common.dao.S3BucketDao;
import com.rboomerang.app.common.hibernate.dao.GenericHibernateDao;
import com.rboomerang.app.domain.S3Bucket;

@Repository(value="S3BucketDao")
@Transactional
public class S3BucketDaoImpl extends GenericHibernateDao<S3Bucket, Long> implements S3BucketDao{
	
		@Autowired
		public S3BucketDaoImpl(SessionFactory sessionFactory) {
			super(sessionFactory);	
				
		}

		@Override
		public S3Bucket getBucketByBucketName(String bucketName) {
			Session session = getSession();
			Criteria criteria = session.createCriteria(S3Bucket.class);
			criteria.add(Restrictions.eq("bucketName", bucketName));
			return (S3Bucket) criteria.uniqueResult();
		}

		
		@Override
		@SuppressWarnings("unchecked")
		public List<S3Bucket> getInstancesByBucketNames(List<String> bucketNames) {
			Session session = getSession();
			Criteria criteria = session.createCriteria(S3Bucket.class);
			criteria.add(Restrictions.in("bucketName", bucketNames));
			return criteria.list();
		}	

	}


