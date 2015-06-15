package com.satish.app.services.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.RdsInstance;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.RdsService;

@Service(value="RdsService")
public class RdsInstanceServiceImpl implements RdsService{

	@Override
	public Collection<RdsInstance> getAllInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RdsInstance> getFilteredInstances(
			Filter<InstanceInfo> info) {
		// TODO Auto-generated method stub
		return null;
	}

}
