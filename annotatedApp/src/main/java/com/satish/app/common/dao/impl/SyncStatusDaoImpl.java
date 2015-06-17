package com.satish.app.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satish.app.common.dao.SyncStatusDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.SyncStatus;

@Repository
@Transactional
public class SyncStatusDaoImpl extends GenericHibernateDao<SyncStatus, Long> implements SyncStatusDao{

	@Autowired
	public SyncStatusDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public SyncStatus getLatestSuccessStatus() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(SyncStatus.class);
		criteria.add(Restrictions.eq("isSuccess", true));
		criteria.addOrder(Order.desc("endTime"));
		criteria.setMaxResults(1);
		return (SyncStatus) criteria.uniqueResult();
		
	}

}
