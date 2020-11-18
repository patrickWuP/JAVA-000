package com.wp;

import com.wp.spring01.Student;
import com.wp.spring02.School;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAutoConfiguration {
    
    @Bean
    public Student student111() {
        Student student = new Student();
        student.setId(111);
        student.setName("WP111");
        return student;
    }
    
    @Bean
    public Student student222() {
        Student student = new Student();
        student.setId(222);
        student.setName("WP222");
        return student;
    }
    
    @Bean
    public School school111() {
        return new School();
    }
}
