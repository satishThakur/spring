package com.satish.app.controllers.aws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.ListTagsForResourceRequest;
import com.amazonaws.services.rds.model.ListTagsForResourceResult;
import com.satish.app.domain.RdsInstance;
import com.satish.app.model.InstanceTagsInfo;
import com.satish.app.services.FilterService;
import com.satish.app.services.RdsService;
import com.satish.app.services.TaggingService;
import com.satish.app.util.ArnUtils;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;
import com.satish.app.util.TagsUtil;

@RestController
@RequestMapping("/rds/instances")
public class RdsInstanceController {
	
	@Autowired
	private RdsService rdsService;
	
	@Autowired
	private FilterService filterService;
	
	@Autowired
	private TaggingService taggingService;
	

	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public Collection<RdsInstance> getAllRdsInstances(){
		return rdsService.getAllInstances();
	}

	@RequestMapping(value="{region}",method=RequestMethod.GET,produces="application/json")
	public List<RdsInstance> getAllRdsInstances(@PathVariable("region") String regionName){
		Region region = RegionsUtils.getRegionFromName(regionName);
		if(region == null){
			throw new RuntimeException("Not a valid region");
		}
		return getAllRdsInstancesInRegion(region);
	}

	@RequestMapping(value="untagged",method=RequestMethod.GET,produces="application/json")
	public List<InstanceTagsInfo<RdsInstance>> getUntaggedInstancesInRegion(@RequestParam(value="region", defaultValue="") List<String> region){
		return taggingService.getUntaggedRdsInstances();
	}


	private List<RdsInstance> getAllRdsInstancesInRegion(Region region){
		AmazonRDS rdsClient = new AmazonRDSClient();
		rdsClient.setRegion(region);
		List<RdsInstance> instances = new ArrayList<RdsInstance>();
		DescribeDBInstancesResult resultSet = rdsClient.describeDBInstances();
		for(DBInstance dbInstance : resultSet.getDBInstances()){
			Map<String,String> tags = getTags(rdsClient, dbInstance, region);
			instances.add(ConverterUtils.convertInstance(dbInstance, region,tags));
		}
		return instances;	
	}



	public static void main(String[] args){
		AmazonRDS rdsClient = new AmazonRDSClient();
		rdsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		DescribeDBInstancesResult resultSet = rdsClient.describeDBInstances();
		for(DBInstance dbInstance : resultSet.getDBInstances()){
			System.out.println(dbInstance);
			System.out.println(getTags(rdsClient,dbInstance,Region.getRegion(Regions.US_WEST_2)));
			break;
		}

	}

	public static Map<String,String> getTags(AmazonRDS rdsClient,DBInstance dbInstance, Region region){
		ListTagsForResourceRequest request = new ListTagsForResourceRequest();
		request.setResourceName(ArnUtils.getArnForDbInstance(region, dbInstance));
		ListTagsForResourceResult result = rdsClient.listTagsForResource(request);
		
		return TagsUtil.flattenRdsTags(result.getTagList());
	}



}
