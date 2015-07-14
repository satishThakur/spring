package com.rboomerang.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.EC2Instance;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.RegionStats;

public interface Ec2Service {
	
	public Collection<EC2Instance> getAllInstances();
	
	public Collection<EC2Instance> getFilteredInstances(Filter<InstanceInfo> info);

	public List<EC2Instance> getInstancesInRegion(String regionName);

	public RegionStats getEc2CurrentStats();

	public Map<String, Integer> getAllClientsStats();

	public List<EC2Instance> getAllCurrentInstances();
	

}
