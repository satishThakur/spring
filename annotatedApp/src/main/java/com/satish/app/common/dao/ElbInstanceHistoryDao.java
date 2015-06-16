package com.satish.app.common.dao;

import java.util.Date;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.ElbInstanceHistory;

public interface ElbInstanceHistoryDao extends HibernateDao<ElbInstanceHistory, Long>{
	
	ElbInstanceHistory getInstanceByElbName(String elbName, Date date);

}
