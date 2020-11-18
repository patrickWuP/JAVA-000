package com.wp.beanassemble;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

    public static void main(String[] args) {
        byXml();
        
//        byAnnotation();

        byComponentScan();
    }
    
    private static void byXml() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("myApplicationContext.xml");
        Family family = ctx.getBean(Family.class);
        System.out.println(family);
        System.out.println("=============================");
    }
    
    private static void byAnnotation() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        Family family = ctx.getBean(Family.class);
        System.out.println(family);
        System.out.println("=============================");
    }
    
    private static void byComponentScan() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ComponentScanConfiguration.class);
        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
    }
}
