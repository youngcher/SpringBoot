package com.ezen.demo.message;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgManager 
{
	private ServletContext sctx;
	private String key = "msgList";
	
	@Autowired
	public void setCtx(ServletContext sctx)
	{
		this.sctx = sctx;
		if(this.sctx.getAttribute(key)==null)
		{
			List<Message> list = new ArrayList<>();
			sctx.setAttribute(key, list);
		}
	}
	
	public List<Message> getList()
	{
		Object obj = this.sctx.getAttribute(key);
		if(obj!=null) 
		{
			List<Message> list = (List<Message>) obj;
			return list;
		}
		return null;
	}
	
	public boolean addMsg(Message msg)
	{
		Object obj = this.sctx.getAttribute(key);
		if(obj!=null) 
		{
			List<Message> list = (List<Message>) obj;
			list.add(msg);
			return true;
		}
		return false;
	}
	
	public Message findMsgByReceiver(String receiver)
	{
		List<Message> list = getList();
		for(int i=0;i<list.size();i++) 
		{
			Message msg = list.get(i);
			if(msg.getReceiver().equals(receiver)) 
			{
				return msg;
			}
		}
		return null;
	}
	
	public Message findMsgBySender(String sender)
	{
		List<Message> list = getList();
		for(int i=0;i<list.size();i++) 
		{
			Message msg = list.get(i);
			if(msg.getSender().equals(sender)) 
			{
				return msg;
			}
		}
		return null;
	}

	public boolean removeMsg(String receiver) 
	{
		List<Message> list = getList();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getReceiver().equals(receiver))
			{
				list.remove(i);
				return true;
			}
		}
		return false;
	}
	
}
