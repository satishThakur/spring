package com.rboomerang.app.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rboomerang.app.common.dao.SyncStatusDao;
import com.rboomerang.app.domain.SyncStatus;
import com.rboomerang.app.services.SyncStatusService;

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
