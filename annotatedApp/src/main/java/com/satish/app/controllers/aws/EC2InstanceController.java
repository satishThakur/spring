package com.satish.app.controllers.aws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.satish.app.model.EC2Instances;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;

@RestController
@RequestMapping("/aws/instances")
public class EC2InstanceController {
	
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public List<EC2Instances> getAllInstances(){
		List<EC2Instances> ec2Instances = new ArrayList<>();
		for(Region region : RegionsUtils.getAllRegions()){
			ec2Instances.addAll(getAllInstancesForRegion(region));
		}
		return ec2Instances;
	}
	
	@RequestMapping(value="{region}",method=RequestMethod.GET,produces="application/json")
	public List<EC2Instances> getInstancesInRegion(@PathVariable("region") String regionName){
		Region region = RegionsUtils.getRegionFromName(regionName);
		if(region == null){
			throw new RuntimeException("Not a valid region");
		}
		return getAllInstancesForRegion(region);
	}
	
	
		
	private List<EC2Instances> getAllInstancesForRegion(Region region){
		AmazonEC2 client = new AmazonEC2Client();
		client.setRegion(region);
		
		DescribeInstancesResult instances = client.describeInstances();
		
		List<EC2Instances> ec2Instances = new ArrayList<>();
		for(Reservation reservation : instances.getReservations()){
			for(Instance instance : reservation.getInstances()){
				ec2Instances.add(ConverterUtils.convertInstance(instance,region));
			}
		}
		
		
		return ec2Instances;
	}
	
	
	
	

}
