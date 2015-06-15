package com.satish.app.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satish.app.common.dao.RdsInstanceDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.RdsInstance;

@Repository
@Transactional
public class RdsInstanceDaoImpl extends GenericHibernateDao<RdsInstance, Long> implements RdsInstanceDao{

	@Autowired
	public RdsInstanceDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public RdsInstance getInstanceByIdAndRegion(String instanceId, String region) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(RdsInstance.class);
		criteria.add(Restrictions.eq("region", region));
		criteria.add(Restrictions.eq("instanceId", instanceId));
		return (RdsInstance) criteria.uniqueResult();
	}

}
