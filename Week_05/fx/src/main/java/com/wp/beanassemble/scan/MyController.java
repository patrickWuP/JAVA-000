package com.wp.beanassemble.scan;

import javax.annotation.Resource;

@org.springframework.stereotype.Controller
public class MyController {
    
    @Resource
    private MyService myService;
}
