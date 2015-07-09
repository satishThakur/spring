package com.satish.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.ElbInstance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;

public interface ElbService {
	
	public Collection<ElbInstance> getAllInstances();
	
	public Collection<ElbInstance> getAllInstancesInRegion(String regionName);
	
	public Collection<ElbInstance> getFilteredInstances(Filter<InstanceInfo> info);

	RegionStats getElbCurrentStats();

	Map<String, Integer> getClientsStats();
	
	public List<ElbInstance> getAllCurrentInstances();

}
