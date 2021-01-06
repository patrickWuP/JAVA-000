package com.wp.example.common.keepalive;

import com.wp.example.common.Operation;
import com.wp.example.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class KeepAliveOperation extends Operation {

    private long time;

    public KeepAliveOperation() {
        this.time = System.nanoTime();
    }
    
    public OperationResult execute() {
        KeepAliveOperationResult keepAliveOperationResult = new KeepAliveOperationResult(time);
        return keepAliveOperationResult;
    }
}
