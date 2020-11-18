package com.wp.customxsd;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SchemaMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("myName.xml");
        People people = (People) context.getBean("myPeople");
        System.out.println(people);
    }
}
