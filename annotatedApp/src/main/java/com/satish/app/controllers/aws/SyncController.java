package com.satish.app.controllers.aws;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.services.InventorySyncService;
import com.satish.app.util.TimeUtils;

@RestController
@RequestMapping("/sync")
public class SyncController {
	
	@Autowired
	private InventorySyncService syncService;
	
	@RequestMapping(produces="application/text")
	public String doSync(){
		
		try{
			Date syncDate = TimeUtils.getCurrentDateWithoutTime();
			syncService.syncInventory(syncDate);
			return "SYNC SUCCESS";
		}catch(Exception ex){
			ex.printStackTrace();
			return "SYNC FAILED!!!";
		}
		
	}

}
