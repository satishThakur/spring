package com.rboomerang.app.common.dao;

import java.util.Collection;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.RdsInstance;

public interface RdsInstanceDao extends HibernateDao<RdsInstance, Long>{

	public RdsInstance getInstanceByIdAndRegion(String instanceId, String region);

	public Collection<RdsInstance> findAllInstancesInRegion(String region);

}
