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

import com.satish.app.common.dao.Ec2InstanceHistoryDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.Ec2InstanceHistory;

@Repository
@Transactional
public class Ec2InstanceHistoryDaoImpl extends GenericHibernateDao<Ec2InstanceHistory, Long> implements Ec2InstanceHistoryDao{

	@Autowired
	public Ec2InstanceHistoryDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Ec2InstanceHistory getInstanceHistoryByDateAndId(Date date,
			String instanceId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ec2InstanceHistory.class);
		criteria.add(Restrictions.eq("date", date));
		criteria.add(Restrictions.eq("instanceId", instanceId));
		return (Ec2InstanceHistory) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ec2InstanceHistory> getAllInstancesOnDate(Date date){
		Session session = getSession();
		Criteria criteria = session.createCriteria(Ec2InstanceHistory.class);
		criteria.add(Restrictions.eq("date", date));
		
		return (List<Ec2InstanceHistory>) criteria.list();
	}

}
