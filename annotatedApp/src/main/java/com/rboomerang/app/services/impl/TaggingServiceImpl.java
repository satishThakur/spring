package com.rboomerang.app.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rboomerang.app.domain.EC2Instance;
import com.rboomerang.app.domain.ElbInstance;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.model.InstanceTagsInfo;
import com.rboomerang.app.services.Ec2Service;
import com.rboomerang.app.services.ElbService;
import com.rboomerang.app.services.RdsService;
import com.rboomerang.app.services.TaggingService;

@Service(value="TaggingService")
public class TaggingServiceImpl implements TaggingService{

	@Autowired
	private Ec2Service ec2Service;
	
	@Autowired
	private RdsService rdsService;
	
	@Autowired
	private ElbService elbService;
	
	private static Logger logger = Logger.getLogger(TaggingServiceImpl.class);
	
	private Set<String> getResourcesInMemory(String resourceName){
		HashSet<String> validResourceSet = new HashSet<String>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream resourceStream = loader.getResourceAsStream(resourceName);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream)))
		{
			String resource;
			while ((resource = br.readLine()) != null) {
				validResourceSet.add(resource.trim());
			}

		} catch (IOException exception) {
			logger.error(exception);
		} 
		return validResourceSet;		
	}
	
	
	private Set<String> getValidSystemTags() {
		return getResourcesInMemory("ValidSystems.txt");
	}

	
	private Set<String> getValidClientTags() {
		return getResourcesInMemory("ValidClients.txt");

	}

	
	private Set<String> getValidEnvTags() {
		return getResourcesInMemory("ValidEnv.txt");
	}
	
	

	@Override
	public List<InstanceTagsInfo<EC2Instance>> getUntaggedEc2Instances() {
		Set<String> validEnvSet = getValidEnvTags();
		Set<String> validClientSet = getValidClientTags();
		Set<String> validSystemSet = getValidSystemTags();
		Collection<EC2Instance> allInstances = ec2Service.getAllCurrentInstances();
		List<InstanceTagsInfo <EC2Instance>> ec2UntaggedInstances = new ArrayList<>();
		for(EC2Instance instance : allInstances){
			List<String> invalidTags = new ArrayList<String>();
			if(!validClientSet.contains(instance.getClient())){
				invalidTags.add("client");
			}
			if(!validEnvSet.contains(instance.getEnv())){
				invalidTags.add("env");
			}
			if(!validSystemSet.contains(instance.getSystem())){
				invalidTags.add("system");
			}
			if (invalidTags.size() > 0){
				InstanceTagsInfo <EC2Instance> instanceTagsInfo = new InstanceTagsInfo<>();
				instanceTagsInfo.setInstance(instance);
				instanceTagsInfo.setInvalidTags(invalidTags);
				ec2UntaggedInstances.add(instanceTagsInfo);
			}
			
		}
		return ec2UntaggedInstances;
}


	@Override
	public List<InstanceTagsInfo<RdsInstance>> getUntaggedRdsInstances() {
		Set<String> validEnvSet = getValidEnvTags();
		Set<String> validClientSet = getValidClientTags();
		Set<String> validSystemSet = getValidSystemTags();
		List<RdsInstance> allInstances = rdsService.getAllCurrentInstances();
		List<InstanceTagsInfo<RdsInstance>> rdsUntaggedInstances = new ArrayList<>();
		for(RdsInstance instance : allInstances){
			List<String> invalidTags = new ArrayList<String>();
			if(!validClientSet.contains(instance.getClient())){
				invalidTags.add("client");
			}
			if(!validEnvSet.contains(instance.getEnv())){
				invalidTags.add("env");
			}
			if(!validSystemSet.contains(instance.getSystem())){
				invalidTags.add("system");
			}
			if (invalidTags.size() > 0){
				InstanceTagsInfo<RdsInstance> instanceTagsInfo = new InstanceTagsInfo<>();
				instanceTagsInfo.setInstance(instance);
				instanceTagsInfo.setInvalidTags(invalidTags);
				rdsUntaggedInstances.add(instanceTagsInfo);
			}
			
		}
		return rdsUntaggedInstances;	
		}


	@Override
	public List<InstanceTagsInfo<ElbInstance>> getUntaggedELbInstances() {
		Set<String> validEnvSet = getValidEnvTags();
		Set<String> validClientSet = getValidClientTags();
		Set<String> validSystemSet = getValidSystemTags();
		List<ElbInstance> allInstances = elbService.getAllCurrentInstances();
		List<InstanceTagsInfo<ElbInstance>> elbUntaggedInstances = new ArrayList<>();
		for(ElbInstance instance : allInstances){
			List<String> invalidTags = new ArrayList<String>();
			if(!validClientSet.contains(instance.getClient())){
				invalidTags.add("client");
			}
			if(!validEnvSet.contains(instance.getEnv())){
				invalidTags.add("env");
			}
			if(!validSystemSet.contains(instance.getSystem())){
				invalidTags.add("system");
			}
			if (invalidTags.size() > 0){
				InstanceTagsInfo<ElbInstance> instanceTagsInfo = new InstanceTagsInfo<>();
				instanceTagsInfo.setInstance(instance);
				instanceTagsInfo.setInvalidTags(invalidTags);
				elbUntaggedInstances.add(instanceTagsInfo);
			}
			
		}
		return elbUntaggedInstances;	
		
	}

}
