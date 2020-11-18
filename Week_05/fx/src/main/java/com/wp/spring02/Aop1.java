package com.wp.spring02;

import org.aspectj.lang.ProceedingJoinPoint;

public class Aop1 {
    
    public void aop1Around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("    ====>around begin ding");
        //调用process()方法才会真正的执行实际被代理的方法
        joinPoint.proceed();

        System.out.println("    ====>around finish ding");
    }
    
    public void start() {
        System.out.println("start");
    }
    
    public void after() {
        System.out.println("after");
    }
    
    public void afterReturn() {
        System.out.println("afterReturn");
    }
}
