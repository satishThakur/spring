package com.satish.app.controllers.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.model.RegionStats;
import com.satish.app.model.ResourcesData;
import com.satish.app.services.Ec2Service;
import com.satish.app.services.ElbService;
import com.satish.app.services.RdsService;

@RestController
@RequestMapping("/resources/stats")
public class ResourcesDataController {
	
	@Autowired
	private Ec2Service ec2Service;
	
	@Autowired
	private ElbService elbService;
	
	@Autowired
	private RdsService rdsService;
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public ResourcesData getResourcesStats(){
		
		ResourcesData data = new ResourcesData();
		data.setEc2Stats(ec2Service.getEc2CurrentStats());
		data.setElbStats(elbService.getElbCurrentStats());
		data.setRdsStats(rdsService.getRdsCurrentStats());
		
		data.setS3Stats(new RegionStats());
		
		
		return data;
	}

}
