package com.wp.rpcclient.controller;

import com.wp.api.OrderService;
import com.wp.api.UserService;
import com.wp.bean.Order;
import com.wp.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

//@RestController
@RequestMapping("/apiController/client")
@Component
public class ApiController {
    
    @Resource
//    @MyService
    private UserService userService;
    
//    @Resource(启动直接报错)
//    @MyService
    @Resource
    private OrderService orderService;
    
    
    @RequestMapping("/findByUserId")
    @ResponseBody
    public User findByUserId(final Long id) {
//        UserService uService = Rpcfx.createInstance(UserService.class, "http://127.0.0.1:8088");
//        return uService.findByUserId(id);
        return userService.findByUserId(id);
    }
    
    @RequestMapping("/findByOrderId")
    public Order findByOrderId(final Long id) {
//        OrderService oService = Rpcfx.createInstance(OrderService.class, "http://127.0.0.1:8088");
//        return oService.findById(id);
        return orderService.findById(id);
    }
    
    //TODO 如何把这块内容弄掉
    @Bean
    public UserService createUserService() {
        return new UserService() {
            @Override
            public User findByUserId(Long id) {
                return null;
            }
        };
    }
    
    @Bean
    public OrderService createOrderService() {
        return new OrderService() {
            @Override
            public Order findById(Long id) {
                return null;
            }
        };
    }
}
