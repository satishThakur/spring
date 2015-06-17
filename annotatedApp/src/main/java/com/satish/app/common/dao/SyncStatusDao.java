package com.satish.app.common.dao;

import com.satish.app.common.hibernate.dao.HibernateDao;
import com.satish.app.domain.SyncStatus;

public interface SyncStatusDao extends HibernateDao<SyncStatus, Long>{
	
	public SyncStatus getLatestSuccessStatus();
}
