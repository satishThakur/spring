package com.satish.app.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.Reservation;
import com.satish.app.common.clients.EC2ClientFactory;
import com.satish.app.common.dao.EC2InstanceDao;
import com.satish.app.domain.EC2Instance;
import com.satish.app.services.EC2InstanceInventoryService;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;

@Service(value="EC2InstanceInventoryService")
@Transactional
public class EC2InstanceInventoryServiceImpl implements EC2InstanceInventoryService{
	
	private static final Logger logger = Logger.getLogger(EC2InstanceInventoryServiceImpl.class);

	@Autowired
	private EC2InstanceDao ec2InstanceDao;


	@Override
	public void sync(){
		logger.info("=============Instance Sync started ===================");
		for(Region region : RegionsUtils.getAllRegions()){
			logger.info("Syncing instances for region: " + region.getName());
			sync(region);
		}
		logger.info("=============Instance Sync end ===================");
	}

	private void sync(Region region){
		AmazonEC2 client = EC2ClientFactory.getEc2Client(region);
		DescribeInstancesResult instances = client.describeInstances();

		for(Reservation reservation : instances.getReservations()){
			for(Instance instance : reservation.getInstances()){
				InstanceStateName state = InstanceStateName.fromValue(instance.getState().getName());			
				if(InstanceStateName.Running.equals(state)){
					logger.debug("persisting: " + instance.getInstanceId());
					EC2Instance  instanceDomain = ConverterUtils.convertInstance(instance,region);
					EC2Instance persisted = ec2InstanceDao.getInstanceByInstanceId(instanceDomain.getInstanceId());
					if(persisted == null){
						ec2InstanceDao.makePersistent(instanceDomain);
					}else{
						logger.info("Already in db ignoring...");
						//in future might need to merge few states ??
					}
				}else{
					logger.info("Ignoring instnace as it is not in running state" + instance.getInstanceId());
				}
			}
		}		
	}

}
