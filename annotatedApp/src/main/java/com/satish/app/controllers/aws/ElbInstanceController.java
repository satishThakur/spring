package com.satish.app.controllers.aws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Region;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersResult;
import com.amazonaws.services.elasticloadbalancing.model.DescribeTagsRequest;
import com.amazonaws.services.elasticloadbalancing.model.DescribeTagsResult;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.Tag;
import com.amazonaws.services.elasticloadbalancing.model.TagDescription;
import com.satish.app.model.ElbInstance;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;
import com.satish.app.util.TagsUtil;

@RestController
@RequestMapping("/elb/instances")
public class ElbInstanceController {
	
	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public List<ElbInstance> getAllElbs(){
		List<ElbInstance> instances = new ArrayList<>();
		for(Region region : RegionsUtils.getAllRegions()){
			instances.addAll(getAllElbsInRegion(region));
		}
		return instances;
	}
	
	@RequestMapping(value="{region}",method=RequestMethod.GET, produces="application/json")
	public List<ElbInstance> getInstancesInRegion(@PathVariable("region") String regionName){
		return getAllElbsInRegion(RegionsUtils.getRegionFromName(regionName));
	}
	
	
	public List<ElbInstance> getAllElbsInRegion(Region region){
		AmazonElasticLoadBalancing elbClient = new AmazonElasticLoadBalancingClient();
		elbClient.setRegion(region);
		
		DescribeLoadBalancersResult result = elbClient.describeLoadBalancers();
		
		Map<String, LoadBalancerDescription> nameToElbMapping = new HashMap<>();
		for(LoadBalancerDescription elb : result.getLoadBalancerDescriptions()){
			nameToElbMapping.put(elb.getLoadBalancerName(), elb);
		}
			
		List<TagDescription> tagDescs = getAllTagsDescription(elbClient,nameToElbMapping);
		List<ElbInstance> instances = new ArrayList<>();
		for(TagDescription tagDesc : tagDescs){
			LoadBalancerDescription elb = nameToElbMapping.get(tagDesc.getLoadBalancerName());
			List<Tag> tags = tagDesc.getTags();
			Map<String,String> deflatedTags = TagsUtil.flattenElbTags(tags);
			instances.add(ConverterUtils.convertElbInstance(elb, region, deflatedTags));
		}
		
		return instances;
		
	}
	
	public List<TagDescription> getAllTagsDescription(AmazonElasticLoadBalancing elbClient,Map<String, LoadBalancerDescription> nameToElbMapping){
		
		List<String> elbNames = new ArrayList<String>(nameToElbMapping.keySet());
		
		int startIndex = 0;
		int totalElbs = elbNames.size();
		List<TagDescription> tagDescs = new ArrayList<>();
		while(startIndex < elbNames.size()){
			
			int endIndex = startIndex + 20;
			if(endIndex > totalElbs){
				endIndex = totalElbs;
			}	
			List<String> subList = elbNames.subList(startIndex, endIndex);
			DescribeTagsRequest request = new DescribeTagsRequest();
			request.setLoadBalancerNames(subList);
			DescribeTagsResult tagsResult = elbClient.describeTags(request);
			tagDescs.addAll(tagsResult.getTagDescriptions());
			startIndex = startIndex + 20;
		}
		
		return tagDescs;
	}
	
	

}
