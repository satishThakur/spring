package com.rboomerang.app.services;

import java.util.List;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.model.InstanceInfo;

public interface FilterService {
	
	public Filter<InstanceInfo> buildFilter(List<String> clients, List<String> systems, List<String> envs);

}
