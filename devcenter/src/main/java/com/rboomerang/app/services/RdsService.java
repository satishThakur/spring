package com.rboomerang.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.RegionStats;

public interface RdsService {
	
	public Collection<RdsInstance> getAllInstances();
	
	public Collection<RdsInstance> getAllInstances(String region);
	
	public Collection<RdsInstance> getFilteredInstances(Filter<InstanceInfo> info);

	RegionStats getRdsCurrentStats();

	Map<String, Integer> getClientsStats();
	
	public List<RdsInstance> getAllCurrentInstances();

}
