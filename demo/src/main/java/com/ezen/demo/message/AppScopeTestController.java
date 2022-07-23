package com.ezen.demo.message;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/app/ctx")
public class AppScopeTestController 
{
	@Autowired
	private MsgService svc;
	
	@GetMapping("")
	@ResponseBody
	public String index()
	{
		Message msg = new Message();
		msg.setReceiver("lora");
		msg.setSender("smith");
		msg.setContents("안녕하세요? Lora 님");
		svc.addMsg(msg);
		return "Application 영역에 메시지(Smith->Lora)를 저장함";
	}
	
	@GetMapping("/msg")
	@ResponseBody
	public String getMsg()
	{
		Message msg = svc.findMsgByReceiver("lora");
		return "Appication 영역으로부터 가져온 메시지=" + msg.getContents();
	}
	
	@GetMapping("/login")
	//@ResponseBody
	public String login(@RequestParam("uid")String uid, 
						@RequestParam("pwd") String pwd,
						HttpSession session, Model model)
	{
		if(uid!=null && !uid.equals("")) 
		{
			session.setAttribute("uid", uid);
			Message msg = svc.findMsgByReceiver(uid);
			if(msg!=null) {
				model.addAttribute("msg", msg);
			}
		}
		return "thymeleaf/msg/msgview";
	}
	
	@PostMapping("/send")
	@ResponseBody
	public String sendMsg(Message msg, HttpSession session)
	{
		msg.setSender((String)session.getAttribute("uid"));
		svc.addMsg(msg);
		return "{\"sent\":true}";
	}
	
	@PostMapping("/find/rec/{receiver}")
	@ResponseBody
	public ResponseEntity<Message> findMsgByReceiver(@PathVariable("receiver") String receiver)
	{
		Message msg = svc.findMsgByReceiver(receiver);
		ResponseEntity<Message> res = new ResponseEntity<>(msg, HttpStatus.OK);
		return res;
	}
	
	@GetMapping("/remove/msg/{receiver}")
	@ResponseBody
	public String removeMsg(@PathVariable("receiver")String receiver)
	{	
		boolean removed = svc.removeMsgByReceiver(receiver);
		return "{\"removed\":"+removed+"}";
	}
}
