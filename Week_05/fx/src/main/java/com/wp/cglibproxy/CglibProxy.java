package com.wp.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy {
    
    public static Object getProxy(Object source) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(source.getClass());
        enhancer.setCallback(new Intercept());
        
        return enhancer.create();
    }
    
    private static class Intercept implements MethodInterceptor {
        
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before intercept");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("after intercept");
            return result;
        }
    }
}
