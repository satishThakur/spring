package com.satish.app.services.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.regions.Region;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.ListTagsForResourceRequest;
import com.amazonaws.services.rds.model.ListTagsForResourceResult;
import com.satish.app.common.clients.RdsClientFactory;
import com.satish.app.common.dao.RdsInstanceDao;
import com.satish.app.domain.RdsInstance;
import com.satish.app.services.RdsInventoryService;
import com.satish.app.util.ArnUtils;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;
import com.satish.app.util.TagsUtil;

@Service(value="RdsInventoryService")
@Transactional
public class RdsInventoryServiceImpl implements RdsInventoryService{
	
	private static Logger logger = Logger.getLogger(RdsInventoryServiceImpl.class);
	
	@Autowired
	private RdsInstanceDao rdsInstanceDao;
	
	@Override
	public void sync() {
		logger.info("====== DB Sync Start ======");
		for(Region region : RegionsUtils.getAllRegions()){
			logger.info("Syncing db instances for region: " + region.getName());
			sync(region);
		}
		logger.info("====== DB Sync End ======");
	}
		
	private void sync(Region region) {
		
		AmazonRDS rdsClient = RdsClientFactory.getRdsClient(region);
		DescribeDBInstancesResult resultSet = rdsClient.describeDBInstances();
		
		for(DBInstance dbInstance : resultSet.getDBInstances()){
			Map<String,String> tags = getTags(rdsClient, dbInstance, region);
			RdsInstance instance = ConverterUtils.convertInstance(dbInstance, region,tags);		
			RdsInstance existingInstance = rdsInstanceDao.getInstanceByIdAndRegion(instance.getInstanceId(), instance.getRegion());
			
			if(existingInstance == null){
				rdsInstanceDao.makePersistent(instance);
			}else{
				logger.info("Already exisit db instance so ignoring: " + instance.getInstanceId());
			}
		}		
		
	}

	private Map<String,String> getTags(AmazonRDS rdsClient,DBInstance dbInstance, Region region){
		ListTagsForResourceRequest request = new ListTagsForResourceRequest();
		request.setResourceName(ArnUtils.getArnForDbInstance(region, dbInstance));
		ListTagsForResourceResult result = rdsClient.listTagsForResource(request);
		
		return TagsUtil.flattenRdsTags(result.getTagList());
	}


}
