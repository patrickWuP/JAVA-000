package com.wp.jdkproxy;

public class JdkProxyTest {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Study youngMan = (Study) JdkProxy.getProxy(new YoungMan(), () -> {
            System.out.println("cycle1 before action");
            return null;
        }, () -> {
            System.out.println("cycle1 after action");
            return null;
        });
        
//        youngMan.learn("Java programming");
//        
//        youngMan.sleep();
        
        //代理代理类
        Study youngMan1 = (Study) JdkProxy.getProxy(youngMan, () -> {
            System.out.println("cycle2 before action");
            return null;
        }, () -> {
            System.out.println("cycle2 after action");
            return null;
        });
        youngMan1.learn("cycle");
        
        youngMan1.sleep();
    }
}
