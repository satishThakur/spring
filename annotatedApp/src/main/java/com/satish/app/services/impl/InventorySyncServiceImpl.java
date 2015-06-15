package com.satish.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.app.services.EC2InstanceInventoryService;
import com.satish.app.services.ElbInventorySyncService;
import com.satish.app.services.InventorySyncService;
import com.satish.app.services.RdsInventoryService;

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
		ec2Inventory.sync();
		rdsInventoryService.sync();
		elbInventorySyncService.sync();
	}

}
