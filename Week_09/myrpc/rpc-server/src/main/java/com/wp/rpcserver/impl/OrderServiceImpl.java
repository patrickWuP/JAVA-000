package com.wp.rpcserver.impl;

import com.wp.api.OrderService;
import com.wp.bean.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service(value = "com.wp.api.OrderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findById(Long id) {
        Order order = new Order();
        order.setCommodityPrice(BigDecimal.TEN);
        order.setState(0);
        order.setId(id);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        return order;
    }
}
