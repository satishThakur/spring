package com.rboomerang.app.controllers.aws;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rboomerang.app.common.filter.Filter;
import com.rboomerang.app.domain.EC2Instance;
import com.rboomerang.app.model.InstanceInfo;
import com.rboomerang.app.model.InstanceTagsInfo;
import com.rboomerang.app.services.Ec2Service;
import com.rboomerang.app.services.FilterService;
import com.rboomerang.app.services.TaggingService;

@RestController
@RequestMapping("/aws/instances")
public class EC2InstanceController {
	
	@Autowired
	private Ec2Service ec2Service;
	
	@Autowired
	private FilterService filterService;
	
	@Autowired
	private TaggingService taggingService;
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public Collection<EC2Instance> getAllInstances(@RequestParam(value="system", defaultValue="") List<String> systems,
			@RequestParam(value="env", defaultValue="") List<String> envs,@RequestParam(value="client", defaultValue="") List<String> clients){
		
		Filter<InstanceInfo> filter = filterService.buildFilter(clients, systems, envs);
		
		return ec2Service.getFilteredInstances(filter);
	}
	
	
	
	@RequestMapping(value="{region}",method=RequestMethod.GET,produces="application/json")
	public List<EC2Instance> getInstancesInRegion(@PathVariable("region") String regionName){
		return ec2Service.getInstancesInRegion(regionName);
	}
	
	@RequestMapping(value="untagged",method=RequestMethod.GET,produces="application/json")
	public List<InstanceTagsInfo<EC2Instance>> getUntaggedInstancesInRegion(@RequestParam(value="region", defaultValue="") List<String> region){
		return taggingService.getUntaggedEc2Instances();
	}
	
}
