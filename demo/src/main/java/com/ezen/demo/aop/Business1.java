package com.ezen.demo.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Business1 
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Dao1 dao1;

    public String calculateSomething() {
        String value = dao1.retrieveSomething();
        logger.info("In Business - {}", value);
        return value;
    }
}