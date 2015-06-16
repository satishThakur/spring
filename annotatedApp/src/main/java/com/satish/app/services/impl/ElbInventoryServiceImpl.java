package com.satish.app.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.regions.Region;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersResult;
import com.amazonaws.services.elasticloadbalancing.model.DescribeTagsRequest;
import com.amazonaws.services.elasticloadbalancing.model.DescribeTagsResult;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.Tag;
import com.amazonaws.services.elasticloadbalancing.model.TagDescription;
import com.satish.app.common.dao.ElbInstanceDao;
import com.satish.app.common.dao.ElbInstanceHistoryDao;
import com.satish.app.domain.ElbInstance;
import com.satish.app.domain.ElbInstanceHistory;
import com.satish.app.services.ElbInventorySyncService;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;
import com.satish.app.util.TagsUtil;

@Service(value="ElbInventorySyncService")
@Transactional
public class ElbInventoryServiceImpl implements ElbInventorySyncService{

	private static Logger logger = Logger.getLogger(ElbInventoryServiceImpl.class);
	
	@Autowired
	private ElbInstanceDao elbInstanceDao;
	
	@Autowired
	private ElbInstanceHistoryDao elbHistoryDao;
	
	@Override
	public void sync(Date syncDate){
		logger.info("================= Started Syncing ELB's ==================");
		for(Region region : RegionsUtils.getAllRegions()){
			logger.info("Syncing elbs for region " + region.getName());
			sync(region,syncDate);
		}
		
		logger.info("================= Syncing ebs Done==================");
		
	}
	
	
	private void sync(Region region, Date date){
		AmazonElasticLoadBalancing elbClient = new AmazonElasticLoadBalancingClient();
		elbClient.setRegion(region);
		
		DescribeLoadBalancersResult result = elbClient.describeLoadBalancers();
		
		Map<String, LoadBalancerDescription> nameToElbMapping = new HashMap<>();
		for(LoadBalancerDescription elb : result.getLoadBalancerDescriptions()){
			nameToElbMapping.put(elb.getLoadBalancerName(), elb);
		}
			
		List<TagDescription> tagDescs = getAllTagsDescription(elbClient,nameToElbMapping);
		for(TagDescription tagDesc : tagDescs){
			LoadBalancerDescription elb = nameToElbMapping.get(tagDesc.getLoadBalancerName());
			List<Tag> tags = tagDesc.getTags();
			Map<String,String> deflatedTags = TagsUtil.flattenElbTags(tags);
			ElbInstance instance = ConverterUtils.convertElbInstance(elb, region, deflatedTags);
			
			syncInstance(instance, date);
			
		}
	}
	
	private void syncInstance(ElbInstance instance, Date date){
		ElbInstance persistedInstance = elbInstanceDao.getInstanceByElbName(instance.getElbName());
		if(persistedInstance == null){
			elbInstanceDao.makePersistent(instance);
		}else{
			logger.info("ElbInstance already present in db ignoring.. " + instance.getElbName());
			//any merging stuff would go here.
		}
		
		ElbInstanceHistory elbHistory = elbHistoryDao.getInstanceByElbName(instance.getElbName(), date);
		if(elbHistory == null){
			elbHistory = new ElbInstanceHistory(instance);
			elbHistory.setDate(date);
			elbHistoryDao.makePersistent(elbHistory);
		}else{
			logger.info("ElbHistory already there for today: " + instance.getElbName());
		}
		
	}
		
	
	private List<TagDescription> getAllTagsDescription(AmazonElasticLoadBalancing elbClient,Map<String, LoadBalancerDescription> nameToElbMapping){
		
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
