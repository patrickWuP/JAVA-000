package com.wp.rpcclient.config;

import com.wp.core.client.Rpcfx;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MyServiceAspect {

    @Value("${server.url}")
    public String serverUrl;
    
    /*@Pointcut("@annotation(com.wp.core.annotation.MyService)")
    public void pointcut(){}*/
    
    @Pointcut("execution(* com.wp.api.*.*(..))")
    public void point(){
        
    }
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        
        String methodName = method.getName();
        String className = method.getDeclaringClass().getInterfaces()[0].getName();
        return Rpcfx.handlerAop(className, methodName, joinPoint.getArgs(), serverUrl);
    }

    @Before(value="point()")
    public void before() {
        System.out.println("before execute");
    }
}
