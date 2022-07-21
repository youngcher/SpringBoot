package com.ezen.demo.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AOP_Test_Controller 
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private Business1 business1;

    @Autowired
    private Business2 business2;

    @GetMapping("/test1")
    public void invokeAOPStuff() {
        logger.info(business1.calculateSomething());
        logger.info(business2.calculateSomething());
    }
}