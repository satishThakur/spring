package com.satish.app.services;

import java.util.List;

import com.satish.app.domain.EC2Instance;
import com.satish.app.domain.ElbInstance;
import com.satish.app.domain.RdsInstance;
import com.satish.app.model.InstanceTagsInfo;

public interface TaggingService {
	
	public List<InstanceTagsInfo<EC2Instance>> getUntaggedEc2Instances();
	
	public List<InstanceTagsInfo<RdsInstance>> getUntaggedRdsInstances();
		
	public List<InstanceTagsInfo<ElbInstance>> getUntaggedELbInstances();
	

	
}
