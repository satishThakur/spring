package com.rboomerang.app.common.dao;

import java.util.Date;
import java.util.List;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.Ec2InstanceHistory;

public interface Ec2InstanceHistoryDao extends HibernateDao<Ec2InstanceHistory, Long>{
	
	Ec2InstanceHistory getInstanceHistoryByDateAndId(Date date, String instanceId);

	List<Ec2InstanceHistory> getAllInstancesOnDate(Date date);
}
