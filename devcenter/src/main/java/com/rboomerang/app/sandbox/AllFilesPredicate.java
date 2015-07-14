package com.rboomerang.app.sandbox;

import java.util.function.Predicate;

import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AllFilesPredicate implements Predicate<S3ObjectSummary>{

	@Override
	public boolean test(S3ObjectSummary t) {
		return true;
	}

}
