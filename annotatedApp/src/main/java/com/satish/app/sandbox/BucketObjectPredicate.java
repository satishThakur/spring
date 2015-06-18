package com.satish.app.sandbox;

import java.util.Collection;
import java.util.Date;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class BucketObjectPredicate implements Predicate<S3ObjectSummary>{
	
	private static Logger logger = Logger.getLogger(BucketObjectPrinter.class);
	
	private Collection<String> keyPrefixes;
	
	private Date modifiedBefore;
	
	public BucketObjectPredicate(Collection<String> prefixes, Date cutOff){
		keyPrefixes = prefixes;
		modifiedBefore = cutOff;
	}

	@Override
	public boolean test(S3ObjectSummary objectSummary) {
		
		return isPrefixCorrect(objectSummary.getKey(), objectSummary) &&
				objectSummary.getSize() > 0 &&
				objectSummary.getLastModified().compareTo(modifiedBefore) <= 0;
	}
	
	private boolean isPrefixCorrect(String key,S3ObjectSummary objectSummary){
		for(String prefix : keyPrefixes){
			if(key.startsWith(prefix)){
				logger.info("Key: " + key + " matches prefix: " + prefix);
				return true;
			}
		}
		
		return false;
	}

}
