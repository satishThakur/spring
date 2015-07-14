package com.rboomerang.app.common.dao;

import java.util.Collection;
import java.util.Date;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.RdsInstanceHistory;

public interface RdsInstanceHistoryDao extends HibernateDao<RdsInstanceHistory, Long>{

	public RdsInstanceHistory getInstanceByDateAndId(Date syncDate, String instanceId,
			String region);
	
	public Collection<RdsInstanceHistory> getAllInstancesOnDate(Date day);

}
