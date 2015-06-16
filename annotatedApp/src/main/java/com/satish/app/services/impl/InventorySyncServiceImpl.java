package com.satish.app.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.services.EC2InstanceInventoryService;
import com.satish.app.services.ElbInventorySyncService;
import com.satish.app.services.InventorySyncService;
import com.satish.app.services.RdsInventoryService;
import com.satish.app.util.TimeUtils;

@Service(value="InventorySyncService")
public class InventorySyncServiceImpl implements InventorySyncService{
	
	@Autowired
	private EC2InstanceInventoryService ec2Inventory;
	
	@Autowired
	private RdsInventoryService rdsInventoryService;
	
	@Autowired
	private ElbInventorySyncService elbInventorySyncService;

	@Override
	public void syncInventory() {
		Date syncDate = TimeUtils.getCurrentDateWithoutTime();
		ec2Inventory.sync(syncDate);
		rdsInventoryService.sync(syncDate);
		elbInventorySyncService.sync(syncDate);
	}

}
