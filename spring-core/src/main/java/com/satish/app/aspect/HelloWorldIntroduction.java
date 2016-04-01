package com.satish.app.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HelloWorldIntroduction {
	
	@DeclareParents(value="com.satish.app.service.GreetWithQouteService+",defaultImpl=DefaultHelloWolrd.class)
	public static HelloWolrd helloWorld;
}
