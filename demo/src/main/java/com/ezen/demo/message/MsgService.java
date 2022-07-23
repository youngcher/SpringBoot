package com.ezen.demo.message;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgService 
{
	private MsgManager mgr;
	
	@Autowired
	private MsgRepository msgRepository;
	
	private ServletContext sctx;
	
	@Autowired
	public void setMgr(MsgManager mgr)
	{
		this.mgr = mgr;
	}
	
	@Autowired
	public void setCtx(ServletContext sctx)
	{
		sctx.setAttribute("svc", this);
		List<Message> list = findAllMsg();
		if(list==null) {
			list = new ArrayList<Message>();
		}
		sctx.setAttribute("msgList", list);
	}
	
	public void addMsg(Message msg)
	{
		mgr.addMsg(msg);
	}
	
	public Message findMsgByReceiver(String receiver)
	{
		return mgr.findMsgByReceiver(receiver);
	}

	public boolean removeMsgByReceiver(String receiver) 
	{
		return mgr.removeMsg(receiver);
	}
	
	public boolean saveMsg(List<Message> list)
	{
		int cnt1 = list.size();
		int cnt2 = msgRepository.saveAll(list).size();
		return cnt1==cnt2;
	}
	
	public List<Message> findAllMsg()
	{
		return msgRepository.findAll();
	}
}
