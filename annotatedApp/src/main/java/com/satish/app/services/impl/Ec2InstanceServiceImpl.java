package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.regions.Region;
import com.satish.app.common.dao.EC2InstanceDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.EC2Instance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.Ec2Service;
import com.satish.app.util.RegionsUtils;

@Service(value="Ec2Service")
@Transactional
public class Ec2InstanceServiceImpl implements Ec2Service{
	
	@Autowired
	private EC2InstanceDao ec2InstanceDao;
	
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

}
