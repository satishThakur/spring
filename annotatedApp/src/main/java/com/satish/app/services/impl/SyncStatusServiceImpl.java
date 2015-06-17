package com.satish.app.services.impl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.common.dao.SyncStatusDao;
import com.satish.app.domain.SyncStatus;
import com.satish.app.services.SyncStatusService;

@Service(value="SyncStatusService")
public class SyncStatusServiceImpl implements SyncStatusService{

	@Autowired
	private SyncStatusDao statusDao;
	
	@Override
	public Date getLastSuccessDate() {
		SyncStatus lastSuccess = statusDao.getLatestSuccessStatus();
		return lastSuccess.getDay();
	}

}
