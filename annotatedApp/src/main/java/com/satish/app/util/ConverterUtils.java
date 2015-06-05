package com.satish.app.util;

import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.rds.model.DBInstance;
import com.satish.app.model.EC2Instances;
import com.satish.app.model.ElbInstance;
import com.satish.app.model.RdsInstance;

public class ConverterUtils {
	
	private static final String DEFAULT_TAG = "UNKNOWN";

	public static EC2Instances convertInstance(Instance instance, Region region) {
		EC2Instances ec2Instance = new EC2Instances();
		ec2Instance.setInstanceId(instance.getInstanceId());
		ec2Instance.setInstanceType(instance.getInstanceType());
		ec2Instance.setRegion(region.getName());
		
		Map<String,String> tags = TagsUtil.flattenTags(instance.getTags());
		
		ec2Instance.setClient(tags.get("client") != null ? tags.get("client") : DEFAULT_TAG);
		ec2Instance.setEnv(tags.get("env") != null ? tags.get("env") : DEFAULT_TAG);
		ec2Instance.setSystem(tags.get("system") != null ? tags.get("system") : DEFAULT_TAG);
		
		return ec2Instance;
	}
	
	public static RdsInstance convertInstance(DBInstance dbInstance,Region region, Map<String,String> tags){
		RdsInstance instance = new RdsInstance();
		instance.setDBInstanceClass(dbInstance.getDBInstanceClass());
		instance.setRegion(region.getName());
		instance.setEndPoint(dbInstance.getEndpoint().getAddress());
		instance.setClient(tags.get("client") != null ? tags.get("client") : DEFAULT_TAG);
		instance.setEnv(tags.get("env") != null ? tags.get("env") : DEFAULT_TAG);
		instance.setSystem(tags.get("system") != null ? tags.get("system") : DEFAULT_TAG);
		
		return instance;
	}
	
	public static ElbInstance convertElbInstance(LoadBalancerDescription elb, Region region, Map<String,String> tags){
		ElbInstance instance = new ElbInstance();
		instance.setElbName(elb.getLoadBalancerName());
		instance.setRegion(region.getName());
		
		instance.setClient(tags.get("client") != null ? tags.get("client") : DEFAULT_TAG);
		instance.setEnv(tags.get("env") != null ? tags.get("env") : DEFAULT_TAG);
		instance.setSystem(tags.get("system") != null ? tags.get("system") : DEFAULT_TAG);
		
		return instance;
	}

}
