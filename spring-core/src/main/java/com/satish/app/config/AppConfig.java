package com.satish.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.satish.app.aspect.MethodExecutionTimeLogging;
import com.satish.app.dao.QouteDao;
import com.satish.app.service.GreetingService;
import com.satish.app.service.QouteService;
import com.satish.app.service.impl.GreetingServiceImpl;

@Configuration
@ComponentScan(basePackageClasses={QouteDao.class,QouteService.class, MethodExecutionTimeLogging.class})
@ImportResource("classpath:application-context.xml")
@PropertySource("classpath:app.properties")
@EnableAspectJAutoProxy
public class AppConfig {
	
	@Bean(name="GreetingService")
	public GreetingService getGreetingService(){
		return new GreetingServiceImpl(); 
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}

}
