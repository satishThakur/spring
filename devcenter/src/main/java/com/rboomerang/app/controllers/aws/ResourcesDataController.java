package com.rboomerang.app.controllers.aws;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rboomerang.app.model.AllResourcesStats;
import com.rboomerang.app.model.ResourcesData;
import com.rboomerang.app.model.ResourcesStatsMapping;
import com.rboomerang.app.services.Ec2Service;
import com.rboomerang.app.services.ElbService;
import com.rboomerang.app.services.RdsService;
import com.rboomerang.app.services.S3Service;

@RestController
@RequestMapping("/resources/stats")
public class ResourcesDataController {

	@Autowired
	private Ec2Service ec2Service;

	@Autowired
	private ElbService elbService;

	@Autowired
	private RdsService rdsService;
	
	@Autowired
	private S3Service s3Service;

	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public ResourcesData getResourcesStats(){

		ResourcesData data = new ResourcesData();
		data.setEc2Stats(ec2Service.getEc2CurrentStats());
		data.setElbStats(elbService.getElbCurrentStats());
		data.setRdsStats(rdsService.getRdsCurrentStats());

		data.setS3Stats(s3Service.getS3BucketCurrentStats());

		return data;
	}

	@RequestMapping(value="clients",method=RequestMethod.GET, produces="application/json")
	public ResourcesStatsMapping<String> getResourcesStatsForClients(){

		ResourcesStatsMapping<String> clientsStats = new ResourcesStatsMapping<>();

		Map<String, AllResourcesStats> statsMapping = new HashMap<>();

		Map<String, Integer> ec2ClientsMapping = ec2Service.getAllClientsStats();

		for(Entry<String,Integer> ec2MappingEntry : ec2ClientsMapping.entrySet()){
			String client = ec2MappingEntry.getKey();
			Integer count = ec2MappingEntry.getValue();

			AllResourcesStats clientStats = statsMapping.get(client);
			if(clientStats == null){
				clientStats = new AllResourcesStats();
				statsMapping.put(client, clientStats);
			}
			clientStats.setEc2InstancesCount(count);			
		}

		Map<String,Integer> rdsClientsMapping = rdsService.getClientsStats();

		for(Entry<String,Integer> rdsMappingEntry : rdsClientsMapping.entrySet()){
			String client = rdsMappingEntry.getKey();
			Integer count = rdsMappingEntry.getValue();

			AllResourcesStats clientStats = statsMapping.get(client);
			if(clientStats == null){
				clientStats = new AllResourcesStats();
				statsMapping.put(client, clientStats);
			}
			clientStats.setRdsInstancesCount(count);			
		}

		Map<String,Integer> elbClientsMapping = elbService.getClientsStats();

		for(Entry<String,Integer> elbMappingEntry : elbClientsMapping.entrySet()){
			String client = elbMappingEntry.getKey();
			Integer count = elbMappingEntry.getValue();

			AllResourcesStats clientStats = statsMapping.get(client);
			if(clientStats == null){
				clientStats = new AllResourcesStats();
				statsMapping.put(client, clientStats);
			}
			clientStats.setElbInstancesCount(count);			
		}
		
		
		Map<String,Integer> s3ClientsMapping = s3Service.getAllClientsStats();

		for(Entry<String,Integer> s3MappingEntry : s3ClientsMapping.entrySet()){
			String client = s3MappingEntry.getKey();
			Integer count = s3MappingEntry.getValue();

			AllResourcesStats clientStats = statsMapping.get(client);
			if(clientStats == null){
				clientStats = new AllResourcesStats();
				statsMapping.put(client, clientStats);
			}
			clientStats.setS3InstancesCount(count);			
		}

		
		

		clientsStats.setStatsMapping(statsMapping);

		return clientsStats;
	}



}
