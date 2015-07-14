package com.rboomerang.app.common.dao;

import com.rboomerang.app.common.hibernate.dao.HibernateDao;
import com.rboomerang.app.domain.SyncStatus;

public interface SyncStatusDao extends HibernateDao<SyncStatus, Long>{
	
	public SyncStatus getLatestSuccessStatus();
}
