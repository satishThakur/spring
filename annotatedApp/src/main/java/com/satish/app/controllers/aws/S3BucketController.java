package com.satish.app.controllers.aws;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.common.filter.Filter;
import com.satish.app.domain.S3Bucket;
import com.satish.app.model.InstanceInfo;
import com.satish.app.services.FilterService;
import com.satish.app.services.S3Service;

@RestController
@RequestMapping("/aws/s3Buckets")

public class S3BucketController {

	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private FilterService filterService;
	
	@RequestMapping(method=RequestMethod.GET,produces="application/json")
	public Collection<S3Bucket> getAllS3Buckets(@RequestParam(value="system", defaultValue="") List<String> systems,
			@RequestParam(value="env", defaultValue="") List<String> envs,@RequestParam(value="client", defaultValue="") List<String> clients){
		
		Filter<InstanceInfo> filter = filterService.buildFilter(clients, systems, envs);
		
		return s3Service.getFilteredBuckets(filter);
	}
	
	
	
}
