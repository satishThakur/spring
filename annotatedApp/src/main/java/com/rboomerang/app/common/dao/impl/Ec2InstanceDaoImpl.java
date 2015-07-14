package com.rboomerang.app.common.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rboomerang.app.common.dao.EC2InstanceDao;
import com.rboomerang.app.common.hibernate.dao.GenericHibernateDao;
import com.rboomerang.app.domain.EC2Instance;

@Repository(value="EC2InstanceDao")
@Transactional
public class Ec2InstanceDaoImpl extends GenericHibernateDao<EC2Instance, Long> implements EC2InstanceDao{
	
	private static Logger logger = Logger.getLogger(Ec2InstanceDaoImpl.class);
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EC2Instance> getInstancesByIds(List<String> instanceIds){
		Session session = getSession();
		Criteria criteria = session.createCriteria(EC2Instance.class);
		criteria.add(Restrictions.in("instanceId", instanceIds));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EC2Instance> getAliveInstancesByIds(List<String> instanceIds){
		Session session = getSession();
		Criteria criteria = session.createCriteria(EC2Instance.class);
		criteria.add(Restrictions.eq("dead", false));
		criteria.add(Restrictions.in("instanceId", instanceIds));
		return criteria.list();
	}
	
	
	@Override
	public Set<String> getAllAliveInstanceIds(){
		Session session = getSession();
		Criteria criteria = session.createCriteria(EC2Instance.class);
		criteria.add(Restrictions.eq("dead", false));
		@SuppressWarnings("unchecked")
		List<EC2Instance> instances = criteria.list();
		
		Set<String> ids = new HashSet<String>();
		for(EC2Instance instance : instances){
			ids.add(instance.getInstanceId());
		}	
		return ids;		
	}
	
	@Override
	public int markInstancesAsDead(Collection<String> instanceIds, Date date){
		Session session = getSession();
		
		String updateQuery = "update EC2Instance set dead = :isDead, deadDate = :date where instanceId in (:ids)";		
		Query query = session.createQuery(updateQuery);
		query.setBoolean("isDead", true);
		query.setDate("date", date);
		query.setParameterList("ids", instanceIds);
		
		int rowsModified = query.executeUpdate();
		logger.info("Asked to update: " + instanceIds.size() + " and updated " + rowsModified);
		
		return rowsModified;
	}
	
	

}
