package com.satish.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.satish.app.common.filter.CompositeFilter;
import com.satish.app.common.filter.Filter;
import com.satish.app.common.filter.SimpleFilter;
import com.satish.app.domain.Client;
import com.satish.app.domain.Environment;
import com.satish.app.domain.ISystem;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.FilterService;
import com.satish.app.util.ConverterUtils;

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
