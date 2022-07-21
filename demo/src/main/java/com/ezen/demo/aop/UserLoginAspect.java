package com.ezen.demo.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration
public class UserLoginAspect {

	@Before("execution(* com.ezen.demo.validation.PersonService.*(..))")
	public void before(JoinPoint joinPoint) {

		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		Object obj = session.getAttribute("uid");
		if(obj==null) {
			log.error("로그인 요구됨");
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		} else {
			log.info("{} 로그인 검사 통과", obj.toString());
		}
	}
	
	
}
