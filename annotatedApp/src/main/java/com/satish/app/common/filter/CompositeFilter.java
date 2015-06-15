package com.satish.app.common.filter;

import com.satish.app.domain.Client;
import com.satish.app.domain.Environment;
import com.satish.app.domain.ISystem;
import com.satish.app.model.InstanceInfo;

public class CompositeFilter implements Filter<InstanceInfo>{

	private Filter<ISystem> systemFilter;

	private Filter<Environment> envFilter;

	private Filter<Client> clientFilter;

	public CompositeFilter(){

	}

	public Filter<ISystem> getSystemFilter() {
		return systemFilter;
	}

	public void setSystemFilter(Filter<ISystem> systemFilter) {
		this.systemFilter = systemFilter;
	}

	public Filter<Environment> getEnvFilter() {
		return envFilter;
	}

	public void setEnvFilter(Filter<Environment> envFilter) {
		this.envFilter = envFilter;
	}

	public Filter<Client> getClientFilter() {
		return clientFilter;
	}

	public void setClientFilter(Filter<Client> clientFilter) {
		this.clientFilter = clientFilter;
	}

	@Override
	public boolean isAllowed(InstanceInfo info){
		boolean isMatch = true;
		if(systemFilter != null){
			isMatch = systemFilter.isAllowed(info.getSystem());
			if(!isMatch)
				return false;
		}

		if(envFilter != null){
			isMatch = envFilter.isAllowed(info.getEnv());
			if(!isMatch)
				return false;
		}

		if(clientFilter != null){
			isMatch = clientFilter.isAllowed(info.getClient());
			if(!isMatch)
				return false;
		}
		return isMatch;		
	}


}
