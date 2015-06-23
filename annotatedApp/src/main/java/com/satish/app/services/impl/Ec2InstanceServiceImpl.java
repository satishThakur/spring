package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Region;
import com.satish.app.common.dao.EC2InstanceDao;
import com.satish.app.common.dao.Ec2InstanceHistoryDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.EC2Instance;
import com.satish.app.domain.Ec2InstanceHistory;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;
import com.satish.app.services.Ec2Service;
import com.satish.app.services.SyncStatusService;
import com.satish.app.util.RegionsUtils;

@Service(value="Ec2Service")
public class Ec2InstanceServiceImpl implements Ec2Service{

	@Autowired
	private EC2InstanceDao ec2InstanceDao;

	@Autowired
	private Ec2InstanceHistoryDao ec2InstanceHistory;

	@Autowired
	private SyncStatusService syncStatsService;

	@Override
	public Collection<EC2Instance> getAllInstances() {
		return ec2InstanceDao.findAll();
	}

	@Override
	public List<EC2Instance> getInstancesInRegion(String regionName){
		Region region = RegionsUtils.getRegionFromName(regionName);
		if(region == null){
			throw new RuntimeException("Not a valid region");
		}
		return ec2InstanceDao.getInstancesInRegion(regionName);
	}


	@Override
	public Collection<EC2Instance> getFilteredInstances(Filter<InstanceInfo> infoFilter) {
		Collection<EC2Instance> allInstances = getAllInstances();
		Collection<EC2Instance> filteredInstances = new ArrayList<>();

		for(EC2Instance instance : allInstances){
			InstanceInfo info = InstanceInfo.getInstanceInfo(instance);
			if(infoFilter.isAllowed(info)){
				filteredInstances.add(instance);
			}
		}

		return filteredInstances;
	}

	@Override
	public RegionStats getEc2CurrentStats() {
		List<EC2Instance> allInstances = getAllCurrentInstances();

		Map<String, Integer> regionCount = new HashMap<String, Integer>();

		for(EC2Instance instance : allInstances){
			String region = instance.getRegion();
			if(regionCount.containsKey(region)){
				regionCount.put(region, regionCount.get(region) + 1);
			}else{
				regionCount.put(region, 1);
			}
		}
		return new RegionStats(regionCount);
	}

	private List<EC2Instance> getAllCurrentInstances(){
		Date successData  = syncStatsService.getLastSuccessDate();
		List<String> instanceIds = getAllInstanceIdsOnDate(successData);
		return ec2InstanceDao.getInstancesByIds(instanceIds);
	}

	private List<String> getAllInstanceIdsOnDate(Date date){
		List<Ec2InstanceHistory> allInstances = ec2InstanceHistory.getAllInstancesOnDate(date);
		List<String> instances = new ArrayList<String>();
		for(Ec2InstanceHistory instanceHistory : allInstances){
			instances.add(instanceHistory.getInstanceId());
		}
		return instances;
	}

	@Override
	public Map<String,Integer> getAllClientsStats(){
		List<EC2Instance> allInstances = getAllCurrentInstances();
		Map<String, Integer> clientsCount = new HashMap<String, Integer>();

		for(EC2Instance instance : allInstances){
			String client = instance.getClient();
			if(clientsCount.containsKey(client)){
				clientsCount.put(client, clientsCount.get(client) + 1);
			}else{
				clientsCount.put(client, 1);
			}
		}
		return clientsCount;
	}

}
