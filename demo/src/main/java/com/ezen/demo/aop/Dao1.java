package com.ezen.demo.aop;

import org.springframework.stereotype.Repository;

@Repository
public class Dao1 
{
    public String retrieveSomething() {
        return "Dao1";
    }
}