package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.satish.app.common.dao.RdsInstanceDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.RdsInstance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;
import com.satish.app.services.RdsService;

@Service(value="RdsService")
public class RdsInstanceServiceImpl implements RdsService{
	
	@Autowired
	private RdsInstanceDao rdsInstanceDao;

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
	public RegionStats getRdsCurrentStats() {
		Collection<RdsInstance> allInstances = getAllInstances();
		
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

}
