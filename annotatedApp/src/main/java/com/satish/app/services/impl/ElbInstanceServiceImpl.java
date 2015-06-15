package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.common.dao.ElbInstanceDao;
import com.satish.app.common.filter.Filter;
import com.satish.app.domain.ElbInstance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.ElbService;

@Service(value="ElbService")
public class ElbInstanceServiceImpl implements ElbService{
	
	@Autowired
	private ElbInstanceDao elbInstanceDao;

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

}
