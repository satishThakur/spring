package com.rboomerang.app.services.impl;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rboomerang.app.common.dao.SyncStatusDao;
import com.rboomerang.app.domain.SyncStatus;
import com.rboomerang.app.services.EC2InstanceInventoryService;
import com.rboomerang.app.services.ElbInventorySyncService;
import com.rboomerang.app.services.InventorySyncService;
import com.rboomerang.app.services.RdsInventoryService;
import com.rboomerang.app.services.S3InventorySyncService;
import com.rboomerang.app.util.TimeUtils;

@Service(value="InventorySyncService")
public class InventorySyncServiceImpl implements InventorySyncService{
	
	private static Logger logger = Logger.getLogger(InventorySyncServiceImpl.class);
	
	private static long FIVE_MINUTES = TimeUnit.MINUTES.toMillis(5);
	
	private static long TWO_HOURS = TimeUnit.HOURS.toMillis(2);
	
	private Timer timer;
	
	@Autowired
	private EC2InstanceInventoryService ec2Inventory;
	
	@Autowired
	private RdsInventoryService rdsInventoryService;
	
	@Autowired
	private ElbInventorySyncService elbInventorySyncService;
	
	@Autowired
	private S3InventorySyncService s3InventorySyncService;
	
	@Autowired
	private SyncStatusDao statusDao;
	
	
	
	public InventorySyncServiceImpl(){
		timer = new Timer("Inventory Sync Timer");
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Date syncDate = TimeUtils.getCurrentDateWithoutTime();
				long startTime = System.currentTimeMillis();
				try{					
					syncInventory(syncDate);
					long timeTaken = System.currentTimeMillis() - startTime;
					logSyncSuccess(syncDate,timeTaken);
				}catch(Exception ex){
					logger.error("Sync failed this time with ", ex);
					logSyncFailed(syncDate, System.currentTimeMillis() - startTime);
				}
				
			}
		}, FIVE_MINUTES, TWO_HOURS);
	}

	protected void logSyncFailed(Date syncDate, long timeTaken) {
		logger.info("Failed Status Sync took " + timeTaken + " ms");
		SyncStatus status = new SyncStatus();
		status.setDay(syncDate);
		status.setEndTime(new Date());
		status.setSuccess(false);
		status.setMessage("Check server logs for errors");
		
		statusDao.makePersistent(status);
	}

	protected void logSyncSuccess(Date syncDate, long timeTaken) {
		logger.info("Success Status Sync took " + timeTaken + " ms");
		SyncStatus status = new SyncStatus();
		status.setDay(syncDate);
		status.setEndTime(new Date());
		status.setSuccess(true);
		
		statusDao.makePersistent(status);
		
	}

	@Override
	public void syncInventory(Date syncDate) {
		
		ec2Inventory.sync(syncDate);
		rdsInventoryService.sync(syncDate);
		elbInventorySyncService.sync(syncDate);
		s3InventorySyncService.sync(syncDate);
	}
	
	@PreDestroy
	public void destroy(){
		logger.info("Destroying the bean");
		if(timer != null){
			logger.info("cleaning up the timer");
			timer.cancel();
			timer = null;
		}
	}

}
