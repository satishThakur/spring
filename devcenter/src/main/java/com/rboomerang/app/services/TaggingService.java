package com.rboomerang.app.services;

import java.util.List;

import com.rboomerang.app.domain.EC2Instance;
import com.rboomerang.app.domain.ElbInstance;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.model.InstanceTagsInfo;

public interface TaggingService {
	
	public List<InstanceTagsInfo<EC2Instance>> getUntaggedEc2Instances();
	
	public List<InstanceTagsInfo<RdsInstance>> getUntaggedRdsInstances();
		
	public List<InstanceTagsInfo<ElbInstance>> getUntaggedELbInstances();
	

	
}
