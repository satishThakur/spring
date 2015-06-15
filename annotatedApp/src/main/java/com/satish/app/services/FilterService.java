package com.satish.app.services;

import java.util.List;

import com.satish.app.common.filter.Filter;
import com.satish.app.model.InstanceInfo;

public interface FilterService {
	
	public Filter<InstanceInfo> buildFilter(List<String> clients, List<String> systems, List<String> envs);

}
