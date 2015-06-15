package com.satish.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.rds.model.DBInstance;
import com.satish.app.common.converters.ClientConverter;
import com.satish.app.common.converters.Converter;
import com.satish.app.common.converters.EnvConverter;
import com.satish.app.common.converters.ISystemConverter;
import com.satish.app.domain.Client;
import com.satish.app.domain.EC2Instance;
import com.satish.app.domain.ElbInstance;
import com.satish.app.domain.Environment;
import com.satish.app.domain.ISystem;
import com.satish.app.domain.RdsInstance;

public class ConverterUtils {
	
	private static final String DEFAULT_TAG = "UNKNOWN";

	public static EC2Instance convertInstance(Instance instance, Region region) {
		EC2Instance ec2Instance = new EC2Instance();
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
		instance.setInstanceId(dbInstance.getDBInstanceIdentifier());
		instance.setClient(tags.get("client") != null ? tags.get("client") : DEFAULT_TAG);
		instance.setEnv(tags.get("env") != null ? tags.get("env") : DEFAULT_TAG);
		instance.setSystem(tags.get("system") != null ? tags.get("system") : DEFAULT_TAG);
		instance.setOwn(tags.get("own") != null ? tags.get("own") : DEFAULT_TAG);
		
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
	
	public static List<Client> convertToClients(List<String> clientNames){
		if(clientNames == null || clientNames.isEmpty()){
			return null;
		}
		return convert(clientNames, new ClientConverter());
	}
	
	public static List<ISystem> convertToSystems(List<String> systemNames){
		if(systemNames == null || systemNames.isEmpty()){
			return null;
		}
		return convert(systemNames, new ISystemConverter());
	}
	
	public static List<Environment> convertToEnvs(List<String> envNames){
		if(envNames == null || envNames.isEmpty()){
			return null;
		}
		return convert(envNames, new EnvConverter());
	}	
	
	private  static <T,U> List<U>  convert(List<T> input, Converter<T,U> converter){
		
		List<U> output = new ArrayList<U>();
		
		for(T value : input){
			output.add(converter.convert(value));
		}
		return output;
	}

}
