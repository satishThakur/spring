package com.satish.app.services;

import java.util.Collection;
import java.util.List;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.EC2Instance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.model.RegionStats;

public interface Ec2Service {
	
	public Collection<EC2Instance> getAllInstances();
	
	public Collection<EC2Instance> getFilteredInstances(Filter<InstanceInfo> info);

	List<EC2Instance> getInstancesInRegion(String regionName);

	public RegionStats getEc2CurrentStats();

}
