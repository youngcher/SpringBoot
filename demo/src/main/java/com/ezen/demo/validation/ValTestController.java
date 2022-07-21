package com.ezen.demo.validation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private PersonRepository repository;
	
	@Autowired
	private PersonService svc;
	
	@GetMapping("")
	@ResponseBody
	public String index()
	{
		return "Validation Test Controller";
	}
	
	@GetMapping("/input") // 입력폼
	public String input_form(Model model)
	{
		model.addAttribute("person", new Person());
		return "thymeleaf/val/input_form";
	}
	
	@PostMapping("/add")
	public String addPerson(@Valid Person person, BindingResult result, Model model) 
	{
		if(result.hasErrors()) //검증오류가 있는 경우
		{
			//error 메시지 출력
//			FieldError ageErr = result.getFieldError("age");
//			String errMsg = ageErr.getDefaultMessage();
//			log.error("age error={}", errMsg);

			List<FieldError> ferrList = result.getFieldErrors();
			for(int i=0; i<ferrList.size(); i++) {
				FieldError fe = ferrList.get(i);
				String fname = fe.getField();
				String msg = fe.getDefaultMessage();
				log.error("{}:{}",fname,msg);
			}
			
			//error 갯수에 따른 해당 error 출력
//			List<ObjectError> list = result.getAllErrors();
//			for(int i=0; i<list.size(); i++) {
//				ObjectError oe = list.get(i);
//				String errMsg = oe.getDefaultMessage();
//				log.error("{}.{}", i+1, errMsg);
//			}
			
			return "thymeleaf/val/input_form"; // 다시 폼으로 
		}
//		repository.save(person);             // 정상 실행
		try {
			svc.addPerson(person); //정상 실행
		} catch(HttpClientErrorException e) {
			model.addAttribute("msg", "로그인 후에 사용할 수 있습니다.");
			return "thymeleaf/val/login";
		}
		return "redirect:/val/test/list";
	}
	
	@GetMapping("/list")
	public ModelAndView list()
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_list");
		mv.addObject("list", repository.findAll());

		return mv;
	}
	
	@GetMapping("/detail/{num}")
	public ModelAndView detail(@PathVariable("num") long num)
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_detail");
		mv.addObject("person", repository.findById(num).get());
		return mv;
	}
	
	@GetMapping("/edit/{num}")
	public ModelAndView edit(@PathVariable("num") long num)
	{
		ModelAndView mv = new ModelAndView("thymeleaf/val/person_edit");
		mv.addObject("person", repository.findById(num).get());
		return mv;
	}
	
	@GetMapping("/delete/{num}")
	public String delete(@PathVariable("num")long num)
	{
		repository.deleteById(num);
		return "redirect:/val/test/list";
	}

}
