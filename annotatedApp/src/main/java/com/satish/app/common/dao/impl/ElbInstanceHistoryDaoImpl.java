package com.satish.app.common.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.satish.app.common.dao.ElbInstanceHistoryDao;
import com.satish.app.common.hibernate.dao.GenericHibernateDao;
import com.satish.app.domain.ElbInstanceHistory;

@Repository
@Transactional
public class ElbInstanceHistoryDaoImpl extends GenericHibernateDao<ElbInstanceHistory, Long> implements ElbInstanceHistoryDao{

	@Autowired
	public ElbInstanceHistoryDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public ElbInstanceHistory getInstanceByElbName(String elbName, Date date) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(ElbInstanceHistory.class);
		criteria.add(Restrictions.eq("elbName", elbName));
		criteria.add(Restrictions.eq("date", date));
		
		return (ElbInstanceHistory) criteria.uniqueResult();
	}

}
