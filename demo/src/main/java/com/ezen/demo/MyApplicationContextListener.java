package com.ezen.demo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.ezen.demo.message.Message;
import com.ezen.demo.message.MsgService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyApplicationContextListener implements ApplicationListener
{
	private ServletContext sctx;
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) 
	{
		//log.info("애플리케이션 이벤트 발생={}", event);
		if(event instanceof ContextClosedEvent)
		{
			log.info("애플리케이션 종료 이벤트");
			MsgService svc = (MsgService)this.sctx.getAttribute("svc");
			List<Message> list = (List<Message>)this.sctx.getAttribute("msgList");
			svc.saveMsg(list);
			log.info("종료전 메시지를 테이블에 저장함");
		}
		else if(event instanceof ContextRefreshedEvent)
		{
			log.info("애플리케이션 리프레시 이벤트");
			ContextRefreshedEvent e = (ContextRefreshedEvent) event;
			
		    ApplicationContext appContext = e.getApplicationContext();
		    if (!(appContext instanceof WebApplicationContext)) return;
		    
		    WebApplicationContext ctx = (WebApplicationContext) e.getApplicationContext();
		    ServletContext context = ctx.getServletContext();
		    this.sctx = context;
		    //context.setAttribute("msg2", "공유 테스트");
		    initContext(context);
		}
	}
	
	private void initContext(ServletContext context)
	{
		List<Message> list = new ArrayList<>();
		context.setAttribute("msgList", list);
	}
}
