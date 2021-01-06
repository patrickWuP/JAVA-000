package com.wp.example.client.codec.dispatcher;

import com.wp.example.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {
    
    private Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();
    
    public void add(Long streamId, OperationResultFuture future) {
        this.map.put(streamId, future);
    }
    
    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = this.map.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            this.map.remove(streamId);
        }
    }
}
