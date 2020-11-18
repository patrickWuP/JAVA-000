package com.wp.spring02;

import com.wp.aop.ISchool;
import com.wp.spring01.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo01 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Student student23 = (Student) context.getBean("student23");
        
        ISchool school = (ISchool) context.getBean("school");
//        ISchool school = new School();//不经过spring管理的对象，没有aop增强效果
        System.out.println(student23);

        school.ding();

    }
}
