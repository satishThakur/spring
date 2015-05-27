package com.satish.soundsystem;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogActions {
	
	@Pointcut("execution(** com.satish.soundsystem.DiscPlayer.play(..))")
	public void play() {}
	
	@Around("play()")
	public void log(ProceedingJoinPoint jp){
		System.out.println("Before Play...");
		try {
			jp.proceed();
		} catch (Throwable e) {
			System.out.println("Play failed!!" + e.getMessage());
		}
		System.out.println("After play...");
	}

}
