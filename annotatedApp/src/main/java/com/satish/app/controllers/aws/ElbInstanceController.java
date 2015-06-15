package com.satish.app.controllers.aws;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.domain.ElbInstance;
import com.satish.app.services.ElbService;
import com.satish.app.services.FilterService;

@RestController
@RequestMapping("/elb/instances")
public class ElbInstanceController {
	
	@Autowired
	private ElbService elbService;
	
	@Autowired
	private FilterService filterService;
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public Collection<ElbInstance> getAllElbs(){
		return elbService.getAllInstances();
	}
	
	@RequestMapping(value="{region}",method=RequestMethod.GET, produces="application/json")
	public Collection<ElbInstance> getInstancesInRegion(@PathVariable("region") String regionName){
		return elbService.getAllInstancesInRegion(regionName);
	}

}
