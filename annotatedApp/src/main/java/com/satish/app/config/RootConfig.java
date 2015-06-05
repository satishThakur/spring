package com.satish.app.config;

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

@Configuration
@ComponentScan({"com.satish.app.services","com.satish.app.repositories"})
@EnableTransactionManagement
public class RootConfig {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	  LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	  sfb.setDataSource(dataSource);
	  sfb.setPackagesToScan(new String[] { "com.satish.app.domain" });
	  Properties props = new Properties();
	  props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
	  props.setProperty("hibernate.hbm2ddl.auto", "create");
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

}
