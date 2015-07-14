package com.rboomerang.app.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.satish.app.services","com.satish.app.repositories", "com.satish.app.common.dao.impl"})
public class RootConfig {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	  LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	  sfb.setDataSource(dataSource);
	  sfb.setPackagesToScan(new String[] { "com.satish.app.domain" });
	  Properties props = new Properties();
	  props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
	  props.setProperty("hibernate.hbm2ddl.auto", "update");
	  props.setProperty("hibernate.show_sql", "true");
	  sfb.setHibernateProperties(props);
	  return sfb;
	}
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/infra");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        return dataSource;
    }
	
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
	
	
	@Bean
	@Autowired
	public TransactionTemplate transactionTemplate(HibernateTransactionManager txManager){
		TransactionTemplate txTemplate = new TransactionTemplate(txManager);
		return txTemplate;
	}

}
