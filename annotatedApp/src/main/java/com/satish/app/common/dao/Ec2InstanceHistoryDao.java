package com.satish.app.common.dao;

import java.util.Date;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.Ec2InstanceHistory;

public interface Ec2InstanceHistoryDao extends HibernateDao<Ec2InstanceHistory, Long>{
	
	Ec2InstanceHistory getInstanceHistoryByDateAndId(Date date, String instanceId);
}
