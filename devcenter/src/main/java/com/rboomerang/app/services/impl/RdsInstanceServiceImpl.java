package com.rboomerang.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rboomerang.app.common.dao.RdsInstanceDao;
import com.rboomerang.app.common.dao.RdsInstanceHistoryDao;
import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.domain.RdsInstanceHistory;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.RegionStats;
import com.rboomerang.app.services.RdsService;
import com.rboomerang.app.services.SyncStatusService;

@Service(value="RdsService")
public class RdsInstanceServiceImpl implements RdsService{

	private static final Logger logger = Logger.getLogger(RdsInstanceServiceImpl.class);

	@Autowired
	private RdsInstanceDao rdsInstanceDao;

	@Autowired
	private SyncStatusService syncStatsService;

	@Autowired
	private RdsInstanceHistoryDao rdsHistoryDao;

	@Override
	public Collection<RdsInstance> getAllInstances() {
		return rdsInstanceDao.findAll();
	}

	@Override
	public Collection<RdsInstance> getFilteredInstances(
			Filter<InstanceInfo> info) {

		Collection<RdsInstance> filteredInstance = new ArrayList<RdsInstance>();

		for(RdsInstance instance : filteredInstance){
			if(info.isAllowed(InstanceInfo.getInstanceInfo(instance))){
				filteredInstance.add(instance);
			}
		}		
		return filteredInstance;
	}

	@Override
	public Collection<RdsInstance> getAllInstances(String region) {
		return rdsInstanceDao.findAllInstancesInRegion(region);
	}

	@Override
	public List<RdsInstance> getAllCurrentInstances(){
		Date successData  = syncStatsService.getLastSuccessDate();
		return getAllInstanceIdsOnDate(successData);

	}

	private List<RdsInstance> getAllInstanceIdsOnDate(Date date){
		Collection<RdsInstanceHistory> allInstances = rdsHistoryDao.getAllInstancesOnDate(date);
		List<RdsInstance> instances = new ArrayList<RdsInstance>();
		for(RdsInstanceHistory instanceHistory : allInstances){
			RdsInstance instance = rdsInstanceDao.getInstanceByIdAndRegion(instanceHistory.getInstanceId(), instanceHistory.getRegion());
			if(instance != null){
				instances.add(instance);
			}else{
				logger.error("RDS instance null for history instance: " + instanceHistory);
			}
		}
		return instances;
	}


	@Override
	public RegionStats getRdsCurrentStats() {
		Collection<RdsInstance> allInstances = getAllCurrentInstances();

		Map<String, Integer> regionCount = new HashMap<String, Integer>();

		for(RdsInstance instance : allInstances){
			String region = instance.getRegion();
			if(regionCount.containsKey(region)){
				regionCount.put(region, regionCount.get(region) + 1);
			}else{
				regionCount.put(region, 1);
			}
		}
		return new RegionStats(regionCount);
	}

	@Override
	public Map<String,Integer> getClientsStats(){
		Collection<RdsInstance> allInstances = getAllCurrentInstances();

		Map<String, Integer> clientCount = new HashMap<String, Integer>();

		for(RdsInstance instance : allInstances){
			String client = instance.getClient();
			if(clientCount.containsKey(client)){
				clientCount.put(client, clientCount.get(client) + 1);
			}else{
				clientCount.put(client, 1);
			}
		}
		return clientCount;
	}

}
