package com.wp.example.common.order;

import com.wp.example.common.Operation;
import com.wp.example.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class OrderOperation extends Operation {
    
    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    public OperationResult execute() {
        log.info("order's executing startup with orderRequest: " + toString());
        //execute order logic
        log.info("order's executing complete");
        OrderOperationResult orderOperationResult = new OrderOperationResult(tableId, dish, true);
        return orderOperationResult;
    }
}
