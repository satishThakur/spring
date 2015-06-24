package com.satish.app.services.impl;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.Reservation;
import com.satish.app.common.clients.EC2ClientFactory;
import com.satish.app.common.dao.EC2InstanceDao;
import com.satish.app.common.dao.Ec2InstanceHistoryDao;
import com.satish.app.domain.EC2Instance;
import com.satish.app.domain.Ec2InstanceHistory;
import com.satish.app.services.EC2InstanceInventoryService;
import com.satish.app.util.ConverterUtils;
import com.satish.app.util.RegionsUtils;

@Service(value="EC2InstanceInventoryService")
public class EC2InstanceInventoryServiceImpl implements EC2InstanceInventoryService{

	private static final Logger logger = Logger.getLogger(EC2InstanceInventoryServiceImpl.class);

	@Autowired
	private EC2InstanceDao ec2InstanceDao;

	@Autowired
	private Ec2InstanceHistoryDao ec2HistoryDao;

	@Autowired
	private TransactionTemplate txTemplate;


	@Override
	public void sync(Date syncDate){

		logger.info("=============Instance Sync started " + syncDate + "===================");
		Set<String> deadInstances = ec2InstanceDao.getAllAliveInstanceIds();
		for(Region region : RegionsUtils.getAllRegions()){
			logger.info("Syncing instances for region: " + region.getName());	
			sync(region,syncDate,deadInstances);
		}
		processDeadInstances(deadInstances,syncDate);
		logger.info("=============Instance Sync end ===================");
	}

	private void sync(Region region, Date date,Set<String> deadInstances){
		
		AmazonEC2 client = EC2ClientFactory.getEc2Client(region);
		DescribeInstancesResult instances = client.describeInstances();
		
		for(Reservation reservation : instances.getReservations()){
			for(Instance instance : reservation.getInstances()){
				InstanceStateName state = InstanceStateName.fromValue(instance.getState().getName());			
				if(InstanceStateName.Running.equals(state)){
					deadInstances.remove(instance.getInstanceId());
					logger.debug("persisting: " + instance.getInstanceId());
					EC2Instance  instanceDomain = ConverterUtils.convertInstance(instance,region);
					syncInstanceInTx(instanceDomain, date);
				}else{
					logger.info("Ignoring instnace as it is not in running state" + instance.getInstanceId());
				}
			}
		}
	}


	private void processDeadInstances(Set<String> deadInstanceIds,Date date) {
		if(deadInstanceIds.size() > 0){
			logger.info("Marking " + deadInstanceIds.size() + " instances dead");
			ec2InstanceDao.markInstancesAsDead(deadInstanceIds, date);
		}else{
			logger.info("Nothing to mark as dead");
		}
	}

	private void syncInstanceInTx(EC2Instance instance, Date date){

		txTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				EC2Instance persisted = ec2InstanceDao.getInstanceByInstanceId(instance.getInstanceId());
				if(persisted == null){
					ec2InstanceDao.makePersistent(instance);
				}else{
					logger.info("Already in db ignoring...");
					//in future might need to merge few states ??
					mergeInstance(instance, persisted);
				}

				Ec2InstanceHistory historyInstance = ec2HistoryDao.getInstanceHistoryByDateAndId(date, instance.getInstanceId());
				if(historyInstance == null){
					historyInstance = new Ec2InstanceHistory(instance);
					historyInstance.setDate(date);
					ec2HistoryDao.makePersistent(historyInstance);
				}else{
					logger.info("History instance already present " + instance.getInstanceId());
				}	

			}
		});		

	}

	//Merge whatever is variable in instance.
	//Like tags can change also the instance might be stopped now resumed, so it is alive after
	//being dead!!!
	private void mergeInstance(EC2Instance newInstance, EC2Instance persistedInstance) {
		boolean isChanged = false;
		if(persistedInstance.isDead()){
			isChanged = true;
			persistedInstance.setDead(false);
			persistedInstance.setDeadDate(null);
		}

		if(!persistedInstance.getSystem().equals(newInstance.getSystem())){
			isChanged = true;
			persistedInstance.setSystem(newInstance.getSystem());
		}

		if(!persistedInstance.getSystem().equals(newInstance.getSystem())){
			isChanged = true;
			persistedInstance.setSystem(newInstance.getSystem());
		}

		if(!persistedInstance.getSystem().equals(newInstance.getSystem())){
			isChanged = true;
			persistedInstance.setSystem(newInstance.getSystem());
		}

		if(isChanged){
			logger.info("Merging instance: " + newInstance.getInstanceId());
			ec2InstanceDao.makePersistent(persistedInstance);
		}

	}

}
