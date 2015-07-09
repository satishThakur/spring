package com.satish.app.controllers.aws;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.domain.ElbInstance;
import com.satish.app.model.InstanceTagsInfo;
import com.satish.app.services.ElbService;
import com.satish.app.services.FilterService;
import com.satish.app.services.TaggingService;

@RestController
@RequestMapping("/elb/instances")
public class ElbInstanceController {
	
	@Autowired
	private ElbService elbService;
	
	@Autowired
	private FilterService filterService;
	
	@Autowired
	private TaggingService taggingService;
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public Collection<ElbInstance> getAllElbs(){
		return elbService.getAllInstances();
	}
	
	@RequestMapping(value="{region}",method=RequestMethod.GET, produces="application/json")
	public Collection<ElbInstance> getInstancesInRegion(@PathVariable("region") String regionName){
		return elbService.getAllInstancesInRegion(regionName);
	}
	
	@RequestMapping(value="untagged",method=RequestMethod.GET,produces="application/json")
	public List<InstanceTagsInfo<ElbInstance>> getUntaggedInstancesInRegion(@RequestParam(value="region", defaultValue="") List<String> region){
		return taggingService.getUntaggedELbInstances();
	}

}
