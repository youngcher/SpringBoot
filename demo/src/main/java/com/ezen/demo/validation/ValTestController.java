package com.ezen.demo.validation;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/val/test")
public class ValTestController 
{
	@Autowired
	private PersonService svc;
	
	@GetMapping("")
	@ResponseBody
	public String index()
	{
		return "Validation Test Controller";
	}
	
	@GetMapping("/input") // 입력폼
	public String input_form(Model model, HttpSession session)
	{
		log.info("입력폼 요청");
		model.addAttribute("person", new Person());
		return "thymeleaf/val/input_form";
	}
	
	@PostMapping("/add")
	public String addPerson(@Valid Person person, BindingResult result, Model model) 
	{
		if(result.hasErrors()) //검증오류가 있는 경우
		{
			/*
			FieldError ageErr = result.getFieldError("age");
			String errMsg = ageErr.getDefaultMessage();
			log.error("age error={}", errMsg);
			*/
			
			/*
			List<FieldError> ferrList = result.getFieldErrors();
			for(int i=0;i<ferrList.size();i++)
			{
				FieldError fe = ferrList.get(i);
				String fname = fe.getField();
				String msg = fe.getDefaultMessage();
				log.error("{}:{}", fname, msg);
			}*/
			
			/*
			 * List<ObjectError> list = result.getAllErrors();
			for(int i=0;i<list.size();i++)
			{
				ObjectError oe = list.get(i);
				String errMsg = oe.getDefaultMessage();
				log.error("{}.{}", i+1, errMsg);
			}*/
			return "thymeleaf/val/input_form"; // 다시 폼으로 
		}
		try {
			svc.save(person);  // 정상 실행
		}catch(HttpClientErrorException e) {  // Aspect에서 로그인 검사, 오류
			model.addAttribute("msg", "로그인 후에 사용할 수 있습니다");
			return "thymeleaf/val/login";
		}
		return "redirect:/val/test/list";
	}
	
	@GetMapping("/list")
	public ModelAndView list()
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_list");
		mv.addObject("list", svc.findAll());

		return mv;
	}
	
	@GetMapping("/detail/{num}")
	public ModelAndView detail(@PathVariable("num") long num)
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_detail");
		mv.addObject("person", svc.findById(num));
		return mv;
	}
	
	@GetMapping("/edit/{num}")
	public ModelAndView edit(@PathVariable("num") long num)
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_edit");
		mv.addObject("person", svc.findById(num));
		return mv;
	}
	
	@GetMapping("/delete/{num}")
	public String delete(@PathVariable("num")long num)
	{
		svc.delete(num);
		return "redirect:/val/test/list";
	}
	
	@GetMapping("/login")
	public String loginForm(HttpSession session)
	{
		return "thymeleaf/val/login";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestParam("uid") String uid,
						@RequestParam("pwd") String pwd,
						HttpSession session)
	{
		// HttpSessionListener에서 @Autowired가 작동하지 않으므로 세션에 Service참조를
		// 저장하여 리스너에서 참조할 수 있도록 준비한다
		session.setAttribute("service", svc);
		
		if(uid!=null && !uid.equals("")) {
			session.setAttribute("uid", uid);
			return "로그인 성공";
		}
		
		return "로그인 실패";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/val/test/input";
	}
}
