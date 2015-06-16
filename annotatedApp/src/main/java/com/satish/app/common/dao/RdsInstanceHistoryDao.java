package com.satish.app.common.dao;

import java.util.Date;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.RdsInstanceHistory;

public interface RdsInstanceHistoryDao extends HibernateDao<RdsInstanceHistory, Long>{

	public RdsInstanceHistory getInstanceByDateAndId(Date syncDate, String instanceId,
			String region);

}
