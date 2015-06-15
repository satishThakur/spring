package com.satish.app.services.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.satish.app.common.filter.Filter;
import com.satish.app.model.ElbInstance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.ElbService;

@Service(value="ElbService")
public class ElbInstanceServiceImpl implements ElbService{

	@Override
	public Collection<ElbInstance> getAllInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ElbInstance> getFilteredInstances(
			Filter<InstanceInfo> info) {
		// TODO Auto-generated method stub
		return null;
	}

}
