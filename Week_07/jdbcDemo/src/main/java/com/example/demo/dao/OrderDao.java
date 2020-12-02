package com.example.demo.dao;

import com.example.demo.domain.Order;

import java.util.List;

public interface OrderDao {
    
    void batchSave(List<Order> list);
    
    void save(Order order);
}
