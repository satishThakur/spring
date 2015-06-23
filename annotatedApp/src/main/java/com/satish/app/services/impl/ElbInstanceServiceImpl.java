package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.common.dao.ElbInstanceDao;
import com.satish.app.common.dao.ElbInstanceHistoryDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.ElbInstance;
import com.satish.app.domain.ElbInstanceHistory;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;
import com.satish.app.services.ElbService;
import com.satish.app.services.SyncStatusService;

@Service(value="ElbService")
public class ElbInstanceServiceImpl implements ElbService{

	@Autowired
	private ElbInstanceDao elbInstanceDao;

	@Autowired
	private SyncStatusService syncStatsService;

	@Autowired
	private ElbInstanceHistoryDao elbHistoryDao;

	@Override
	public Collection<ElbInstance> getAllInstances() {
		return elbInstanceDao.findAll();
	}

	@Override
	public Collection<ElbInstance> getFilteredInstances(
			Filter<InstanceInfo> info) {
		Collection<ElbInstance> filteredInstances = new ArrayList<ElbInstance>();

		for(ElbInstance instance : getAllInstances()){
			if(info.isAllowed(InstanceInfo.getInstanceInfo(instance))){
				filteredInstances.add(instance);
			}
		}

		return filteredInstances;
	}

	@Override
	public Collection<ElbInstance> getAllInstancesInRegion(String regionName) {
		return elbInstanceDao.getInstancesInRegion(regionName);
	}

	private List<ElbInstance> getAllCurrentInstances(){
		Date successData  = syncStatsService.getLastSuccessDate();
		List<String> instanceIds = getAllElbNamesOnDate(successData);
		return elbInstanceDao.getInstancesByNames(instanceIds);
	}

	private List<String> getAllElbNamesOnDate(Date date){
		List<ElbInstanceHistory> allInstances = elbHistoryDao.getAllInstancesOnDate(date);
		List<String> elbNames = new ArrayList<String>();
		for(ElbInstanceHistory instanceHistory : allInstances){
			elbNames.add(instanceHistory.getElbName());
		}
		return elbNames;
	}


	@Override
	public RegionStats getElbCurrentStats() {
		Collection<ElbInstance> allInstances = getAllCurrentInstances();

		Map<String, Integer> regionCount = new HashMap<String, Integer>();

		for(ElbInstance instance : allInstances){
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
		Collection<ElbInstance> allInstances = getAllCurrentInstances();

		Map<String, Integer> clientCount = new HashMap<String, Integer>();

		for(ElbInstance instance : allInstances){
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
