package com.ezen.demo.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class UserAccessAspect {

	
//	HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
	
	//주 로직 -> 어딘가에 있는 메소드(Core Concern : 비지니스 로직)를 실행하기 전에 실행하는 의미
	@After("execution(* com.ezen.demo.aop.*.*(..))") //PointCut
	public void before(JoinPoint joinPoint) {
	
		//Aspect는 부가적인 로직
		//Advice
//		log.info("Check for user access");
//		log.info("Allowed execution for {}", joinPoint);
		log.info("실행 완료");
	}
	
//	@AfterReturning("execution(* com.ezen.demo.aop.*.*(..))")
//	public void afterReturning(JoinPoint joinPoint, Object result){
//		
//	}
	
//	@Around("execution(* com.ezen.demo.aop.BankService.*(..))")
//	public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
//		log.info("The method aroundAdvice() before invokation of the method " + jp.getSignature().getName() + " method");
//		try {
//			jp.proceed(); // 실제 호출된 Core Concern 이 실행됨
//		} finally {
//		
//		} 
//		log.info("The method aroundAdvice() after invokation of the method " + jp.getSignature().getName() + " method");
//	}
	
}
