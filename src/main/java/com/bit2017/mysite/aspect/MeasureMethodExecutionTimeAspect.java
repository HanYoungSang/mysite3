package com.bit2017.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureMethodExecutionTimeAspect {

	@Around ("execution( * *..repository.*.*(..)) || execution( * *..service.*.*(..))")
	public Object around ( ProceedingJoinPoint pjp ) throws Throwable {

//		System.out.println("TEST");
		//before advices
		StopWatch sw = new StopWatch();
		sw.start();
		
		// dao method 실행
		Object result = pjp.proceed();
		
		//after advices
		sw.stop();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;
		
		System.out.println("[ExecutionTime]" + taskName + ":" + sw.getLastTaskTimeMillis());
		
		
		return result;
		
	}
}
