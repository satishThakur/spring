package com.rboomerang.app.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rboomerang.app.common.dao.ElbInstanceDao;
import com.rboomerang.app.common.hibernate.dao.GenericHibernateDao;
import com.rboomerang.app.domain.ElbInstance;

@Repository(value="ElbInstanceDao")
@Transactional
public class ElbInstanceDaoImpl extends GenericHibernateDao<ElbInstance, Long> implements ElbInstanceDao{
	
	@Autowired
	public ElbInstanceDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public ElbInstance getInstanceByElbName(String elbName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ElbInstance.class);
		criteria.add(Restrictions.eq("elbName", elbName));
		return (ElbInstance) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElbInstance> getInstancesInRegion(String regionName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ElbInstance.class);
		criteria.add(Restrictions.eq("region", regionName));
		return criteria.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ElbInstance> getInstancesByNames(List<String> elbNames) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ElbInstance.class);
		criteria.add(Restrictions.in("elbName", elbNames));
		return criteria.list();
	}

}
