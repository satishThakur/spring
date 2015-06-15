package com.satish.app.common.dao;

import java.util.List;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.EC2Instance;

public interface EC2InstanceDao extends HibernateDao<EC2Instance, Long>{
	
	public EC2Instance getInstanceByInstanceId(String instanceId);

	public List<EC2Instance> getInstancesInRegion(String regionName);

}
