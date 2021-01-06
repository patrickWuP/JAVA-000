package com.wp.example.common.order;

import com.wp.example.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {
    
    private int tableId;
    
    private String dish;
    
    private boolean complete;

    public OrderOperationResult(int tableId, String dish, boolean complete) {
        this.tableId = tableId;
        this.dish = dish;
        this.complete = complete;
    }
}
