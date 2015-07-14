package com.rboomerang.app.common.dao;

import java.util.Date;
import java.util.List;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.ElbInstanceHistory;

public interface ElbInstanceHistoryDao extends HibernateDao<ElbInstanceHistory, Long>{
	
	ElbInstanceHistory getInstanceByElbName(String elbName, Date date);

	List<ElbInstanceHistory> getAllInstancesOnDate(Date date);

}
