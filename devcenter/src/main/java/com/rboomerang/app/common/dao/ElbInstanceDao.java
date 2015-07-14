package com.rboomerang.app.common.dao;

import java.util.List;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.ElbInstance;

public interface ElbInstanceDao extends HibernateDao<ElbInstance, Long>{

	ElbInstance getInstanceByElbName(String elbName);

	List<ElbInstance> getInstancesInRegion(String regionName);

	List<ElbInstance> getInstancesByNames(List<String> instanceIds);

}
