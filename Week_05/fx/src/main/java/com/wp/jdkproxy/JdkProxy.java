package com.wp.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import java.util.function.Supplier;

public class JdkProxy {
    
    public static Object getProxy(Object source, Supplier before, Supplier after) {
        JdkInvocationHandler handler = new JdkInvocationHandler(source, before, after);
        return Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), handler);
    }
    
    private static class JdkInvocationHandler implements InvocationHandler {
        //前置函数
        private Supplier before;
        //后置函数
        private Supplier after;
        
        private Object source;

        JdkInvocationHandler(Object source, Supplier before, Supplier after) {
            this.source = source;
            this.before = before;
            this.after = after;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            before.get();
            Object result = method.invoke(source, args);
            after.get();
            return result;
        }
    }
    
}
