package com.example.demo.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    
    private Integer id;
    
    private Integer userId;
    
    private Integer commodityId;
    
    private BigDecimal commodityPrice;
    
    private Integer state;
    
    private Date createTime;
    
    private Date modifyTime;
}
