package com.rboomerang.app.common.dao.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rboomerang.app.common.dao.RdsInstanceHistoryDao;
import com.rboomerang.app.common.hibernate.dao.GenericHibernateDao;
import com.rboomerang.app.domain.RdsInstanceHistory;

@Repository
@Transactional
public class RdsInstanceHistoryDaoImpl extends GenericHibernateDao<RdsInstanceHistory, Long> implements RdsInstanceHistoryDao{

	@Autowired
	public RdsInstanceHistoryDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		
	}

	@Override
	public RdsInstanceHistory getInstanceByDateAndId(Date syncDate,
			String instanceId, String region) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RdsInstanceHistory.class);
		criteria.add(Restrictions.eq("date", syncDate));
		criteria.add(Restrictions.eq("region", region));
		criteria.add(Restrictions.eq("instanceId", instanceId));
		return (RdsInstanceHistory) criteria.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<RdsInstanceHistory> getAllInstancesOnDate(Date day) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RdsInstanceHistory.class);
		criteria.add(Restrictions.eq("date", day));
		
		return criteria.list();
	}

}
