package com.satish.app.dao.impl;

import java.util.Random;

import org.springframework.stereotype.Repository;

import com.satish.app.dao.QouteDao;

@Repository("QouteDao")
public class QouteDaoImpl implements QouteDao{
	
	private static final String[] qoutes = {
			"Java is to JavaScript what Car is to Carpet",
			"It's hard enough to find an error in your code when you're looking for it; it's even harder when you've assumed your code is error-free",
			"If debugging is the process of removing software bugs, then programming must be the process of putting them in.",
			"Debugging is twice as hard as writing the code in the first place. Therefore, if you write the code as cleverly as possible, you are, by definition, not smart enough to debug it."						
	};

	@Override
	public String getQoute() {
		
		return qoutes[new Random().nextInt(qoutes.length)];
	}

}
