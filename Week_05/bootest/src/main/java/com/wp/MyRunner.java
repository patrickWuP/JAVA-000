package com.wp;

import com.wp.spring02.School;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRunner implements CommandLineRunner {
    @Resource(name = "school111")
    private School school;
    
    @Override
    public void run(String... args) throws Exception {
        school.ding();
    }
}
