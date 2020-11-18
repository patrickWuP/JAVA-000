package com.wp.cglibproxy;

public class CglibProxyTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        
        Cat cat = (Cat) CglibProxy.getProxy(new Cat());
        cat.eat("milk");
        
        cat.sleep("bedroom");
    }
}
