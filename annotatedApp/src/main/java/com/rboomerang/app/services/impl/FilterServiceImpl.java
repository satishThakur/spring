package com.rboomerang.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rboomerang.app.common.filter.CompositeFilter;
import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.common.filter.SimpleFilter;
import com.rboomerang.app.domain.Client;
import com.rboomerang.app.domain.Environment;
import com.rboomerang.app.domain.ISystem;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.services.FilterService;
import com.rboomerang.app.util.ConverterUtils;

@Service(value="FilterService")
public class FilterServiceImpl implements FilterService{

	@Override
	public Filter<InstanceInfo> buildFilter(List<String> clientNames,
			List<String> systemNames, List<String> envNames) {
		CompositeFilter filter = new CompositeFilter();
		Filter<Client> clientsFilter = null;
		Filter<ISystem> systemFilter = null;
		Filter<Environment> envsFilter = null;

		if(clientNames != null && !clientNames.isEmpty()){
			List<Client> clients = ConverterUtils.convertToClients(clientNames);
			clientsFilter = new SimpleFilter<Client>(clients);
		}

		if(systemNames != null && !systemNames.isEmpty()){
			List<ISystem> systems = ConverterUtils.convertToSystems(systemNames);
			systemFilter = new SimpleFilter<ISystem>(systems);
		}

		if(envNames != null && !envNames.isEmpty()){
			List<Environment> envs = ConverterUtils.convertToEnvs(envNames);
			envsFilter = new SimpleFilter<Environment>(envs);
		}
		filter.setClientFilter(clientsFilter);
		filter.setEnvFilter(envsFilter);
		filter.setSystemFilter(systemFilter);		
		return filter;
	}

}
