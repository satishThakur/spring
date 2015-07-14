package com.rboomerang.app.util;

import com.amazonaws.regions.Region;
import com.amazonaws.services.rds.model.DBInstance;

public class ArnUtils {
	
	private static final String ACCOUNT_NUMBER =  "208876916689";
	private static final String DB_ARN="arn:aws:rds:";
	
	public static String getArnForDbInstance(Region region, DBInstance dbInstance){
		return  DB_ARN + region.getName() + ":" + ACCOUNT_NUMBER + ":" + "db" + ":" + dbInstance.getDBInstanceIdentifier();
	}

}
