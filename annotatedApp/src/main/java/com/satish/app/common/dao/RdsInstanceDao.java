package com.satish.app.common.dao;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.RdsInstance;

public interface RdsInstanceDao extends HibernateDao<RdsInstance, Long>{

	public RdsInstance getInstanceByIdAndRegion(String instanceId, String region);

}
