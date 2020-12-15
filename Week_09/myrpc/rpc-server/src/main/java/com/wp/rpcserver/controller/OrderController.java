package com.wp.rpcserver.controller;

import com.wp.api.OrderService;
import com.wp.bean.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/orderController")
public final class OrderController {
    
    @Resource
    private OrderService orderService;

    @RequestMapping("/findByOrderId")
    private Order findByOrderId(final Long id) {
        return orderService.findById(id);
    }
    
}
