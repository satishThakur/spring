package com.satish.app.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtils {
	
	public static Date getCurrentDateWithoutTime(){
		
		LocalDate localDate = LocalDate.now();
		
		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	
	
	
	public static void main(String[] args){
		System.out.println(getCurrentDateWithoutTime());
	}

}
