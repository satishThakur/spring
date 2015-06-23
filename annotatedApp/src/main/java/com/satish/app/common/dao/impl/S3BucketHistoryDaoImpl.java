package com.satish.app.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satish.app.common.dao.S3BucketHistoryDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.S3BucketHistory;

@Repository
@Transactional
public class S3BucketHistoryDaoImpl extends GenericHibernateDao<S3BucketHistory, Long> implements S3BucketHistoryDao{

	@Autowired
	public S3BucketHistoryDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public S3BucketHistory getInstanceHistoryByDateAndName(Date date,String bucketName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(S3BucketHistory.class);
		criteria.add(Restrictions.eq("date", date));
		criteria.add(Restrictions.eq("bucketName", bucketName));
		return (S3BucketHistory) criteria.uniqueResult();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<S3BucketHistory> getAllInstancesOnDate(Date date) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(S3BucketHistory.class);
		criteria.add(Restrictions.eq("date", date));	
		return criteria.list();
	}

}
