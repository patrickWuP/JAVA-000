package com.wp.beanassemble.scan;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class MyService {
    
    @Autowired
    private MyRepository repository;
}
