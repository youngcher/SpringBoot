package com.ezen.demo.validation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Configuration 
public class UserTrackAspect 
{
	@AfterReturning(value="execution(* com.ezen.demo.validation.PersonService.*(..))", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) 
	{
		String methodName = joinPoint.toShortString().split("\\.")[1].split("\\(")[0];
		log.info("{} returned with value {}", methodName, result);
		
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		Object obj = session.getAttribute("histList");
		if (obj==null) initSession(session);
		
		RequestMenu menu = new RequestMenu();
		menu.setUserid((String)session.getAttribute("uid"));
		menu.setReqtime(new java.sql.Timestamp(System.currentTimeMillis()));
		menu.setSvcname(methodName);
		
		log.info("menu={}", menu);
		
		List<RequestMenu> list = (List<RequestMenu>)session.getAttribute("histList");
		if(list.size()==0) {
			list.add(menu);
			log.info("세션에 이동내역 저장 완료");
			return;
		}
		
		if(list.get(list.size()-1).getSvcname().equals(menu.getSvcname())) {
		}else {
			list.add(menu);
			log.info("세션에 이동내역 저장 완료");
		}
	}
	
	private void initSession(HttpSession s)
	{
		List<RequestMenu> list = new ArrayList<>();
		s.setAttribute("histList", list);
	}
}
