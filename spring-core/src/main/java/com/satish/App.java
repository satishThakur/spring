package com.satish;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.satish.app.aspect.HelloWolrd;
import com.satish.app.config.AppConfig;
import com.satish.app.service.GreetWithQouteService;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        //ctx.getBean(GreetingService.class).sayGreetings();     
        //ctx.getBean(QouteService.class).qoute();
        
        GreetWithQouteService greetingService = ctx.getBean(GreetWithQouteService.class);
        greetingService.greetWithQoute();
        HelloWolrd helloWorld = (HelloWolrd)greetingService;
        helloWorld.say();
        
    }
}
