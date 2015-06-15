package com.satish.app.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satish.app.common.dao.EC2InstanceDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.EC2Instance;

@Repository(value="EC2InstanceDao")
@Transactional
public class Ec2InstanceDaoImpl extends GenericHibernateDao<EC2Instance, Long> implements EC2InstanceDao{
	
	@Autowired
	public Ec2InstanceDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);	
			
	}

	@Override
	public EC2Instance getInstanceByInstanceId(String instanceId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EC2Instance.class);
		criteria.add(Restrictions.eq("instanceId", instanceId));
		return (EC2Instance) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EC2Instance> getInstancesInRegion(String regionName) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(EC2Instance.class);
		criteria.add(Restrictions.eq("region", regionName));
		return criteria.list();
	}
	
	

}
