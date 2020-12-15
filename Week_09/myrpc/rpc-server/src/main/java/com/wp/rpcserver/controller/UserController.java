package com.wp.rpcserver.controller;

import com.wp.api.UserService;
import com.wp.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userController")
public final class UserController {
    
    @Resource
    UserService userService;

    @RequestMapping("/findByUserId")
    private User findByUserId(final Long id) {
        return userService.findByUserId(id);
    }
    
    
}
