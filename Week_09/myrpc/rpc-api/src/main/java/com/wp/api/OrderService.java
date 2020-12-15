package com.wp.api;

import com.wp.bean.Order;

public interface OrderService {
    
    Order findById(Long id);
}
