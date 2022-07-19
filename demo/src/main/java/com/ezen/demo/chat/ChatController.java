package com.ezen.demo.chat;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ws")
public class ChatController
{
   @GetMapping("")
   @ResponseBody
   public String index()
   {
      return "WebSocket Test";
   }
   
   @RequestMapping(value="/chat", method=RequestMethod.GET)
    public String chat(Locale locale, Model model) {
        return "chat/chat";
    }

}