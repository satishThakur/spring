package com.rboomerang.app.services.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Region;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.ListTagsForResourceRequest;
import com.amazonaws.services.rds.model.ListTagsForResourceResult;
import com.rboomerang.app.common.clients.RdsClientFactory;
import com.rboomerang.app.common.dao.RdsInstanceDao;
import com.rboomerang.app.common.dao.RdsInstanceHistoryDao;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.domain.RdsInstanceHistory;
import com.rboomerang.app.services.RdsInventoryService;
import com.rboomerang.app.util.ArnUtils;
import com.rboomerang.app.util.ConverterUtils;
import com.rboomerang.app.util.RegionsUtils;
import com.rboomerang.app.util.TagsUtil;

@Service(value="RdsInventoryService")
public class RdsInventoryServiceImpl implements RdsInventoryService{
	
	private static Logger logger = Logger.getLogger(RdsInventoryServiceImpl.class);
	
	@Autowired
	private RdsInstanceDao rdsInstanceDao;
	
	@Autowired
	private RdsInstanceHistoryDao rdsHistoryDao;
	
	@Override
	public void sync(Date syncDate) {
		logger.info("====== DB Sync Start ======");
		for(Region region : RegionsUtils.getAllRegions()){
			logger.info("Syncing db instances for region: " + region.getName());
			sync(region,syncDate);
		}
		logger.info("====== DB Sync End ======");
	}
		
	private void sync(Region region, Date syncDate) {
		
		AmazonRDS rdsClient = RdsClientFactory.getRdsClient(region);
		DescribeDBInstancesResult resultSet = rdsClient.describeDBInstances();
		
		for(DBInstance dbInstance : resultSet.getDBInstances()){
			Map<String,String> tags = getTags(rdsClient, dbInstance, region);
			RdsInstance instance = ConverterUtils.convertInstance(dbInstance, region,tags);		
			syncInstance(instance, syncDate);
		}		
		
	}
	
	private void syncInstance(RdsInstance instance, Date syncDate){
		RdsInstance existingInstance = rdsInstanceDao.getInstanceByIdAndRegion(instance.getInstanceId(), instance.getRegion());
		
		if(existingInstance == null){
			rdsInstanceDao.makePersistent(instance);
		}else{
			logger.info("Already exisit db instance so ignoring: " + instance.getInstanceId());
		}
		
		RdsInstanceHistory rdsHistory = rdsHistoryDao.getInstanceByDateAndId(syncDate, instance.getInstanceId(), instance.getRegion());
		if(rdsHistory == null){
			rdsHistory = new RdsInstanceHistory(instance);
			rdsHistory.setDate(syncDate);
			rdsHistoryDao.makePersistent(rdsHistory);
		}else{
			logger.info("Instance history already there " + instance.getInstanceId());
		}
	}

	private Map<String,String> getTags(AmazonRDS rdsClient,DBInstance dbInstance, Region region){
		ListTagsForResourceRequest request = new ListTagsForResourceRequest();
		request.setResourceName(ArnUtils.getArnForDbInstance(region, dbInstance));
		ListTagsForResourceResult result = rdsClient.listTagsForResource(request);
		
		return TagsUtil.flattenRdsTags(result.getTagList());
	}


}
