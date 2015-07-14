package com.rboomerang.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.ElbInstance;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.RegionStats;

public interface ElbService {
	
	public Collection<ElbInstance> getAllInstances();
	
	public Collection<ElbInstance> getAllInstancesInRegion(String regionName);
	
	public Collection<ElbInstance> getFilteredInstances(Filter<InstanceInfo> info);

	RegionStats getElbCurrentStats();

	Map<String, Integer> getClientsStats();
	
	public List<ElbInstance> getAllCurrentInstances();

}
