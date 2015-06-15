package com.satish.app.services;

import java.util.Collection;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.RdsInstance;
import com.satish.app.model.InstanceInfo;

public interface RdsService {
	
	public Collection<RdsInstance> getAllInstances();
	
	public Collection<RdsInstance> getAllInstances(String region);
	
	public Collection<RdsInstance> getFilteredInstances(Filter<InstanceInfo> info);

}
