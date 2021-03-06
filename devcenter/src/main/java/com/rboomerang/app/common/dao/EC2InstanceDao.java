package com.rboomerang.app.common.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.EC2Instance;

public interface EC2InstanceDao extends HibernateDao<EC2Instance, Long>{
	
	public EC2Instance getInstanceByInstanceId(String instanceId);

	public List<EC2Instance> getInstancesInRegion(String regionName);

	public List<EC2Instance> getInstancesByIds(List<String> instanceIds);

	public Set<String> getAllAliveInstanceIds();

	public int markInstancesAsDead(Collection<String> instanceIds, Date date);

	List<EC2Instance> getAliveInstancesByIds(List<String> instanceIds);

}
