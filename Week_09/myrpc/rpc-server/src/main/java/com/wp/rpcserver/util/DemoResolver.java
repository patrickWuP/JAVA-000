package com.wp.rpcserver.util;

import com.wp.core.api.RpcfxResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DemoResolver <T> implements RpcfxResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;
    
    @Override
    public T resolve(String serviceClass) {
        return (T) this.applicationContext.getBean(serviceClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
