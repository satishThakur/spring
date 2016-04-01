package com.satish.app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodExecutionTimeLogging {
	
	@Pointcut("execution(public * com.satish.app.service.impl.GreetWithQouteServiceImpl.greetWithQoute(..))")
	public void performGreeting(){
		
	}
	@Around("execution(public * com.satish.app.service.impl.GreetWithQouteServiceImpl.greetWithQoute(..))")
	public void timeMethodExecution(ProceedingJoinPoint jp){
		String name = jp.getSignature().getName();
		long mills = System.currentTimeMillis();
		try{
			jp.proceed();
		} catch (Throwable e) {
			
		}finally{
			System.out.println(name + " took: " + (System.currentTimeMillis() - mills) + " ms" );
		}
		
	}

}
