package com.wp.example.common;

import com.wp.example.common.auth.AuthOperation;
import com.wp.example.common.auth.AuthOperationResult;
import com.wp.example.common.keepalive.KeepAliveOperation;
import com.wp.example.common.keepalive.KeepAliveOperationResult;
import com.wp.example.common.order.OrderOperation;
import com.wp.example.common.order.OrderOperationResult;

import java.util.Arrays;

public enum OperationType {
    
    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepAliveOperation.class, KeepAliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz,
                  Class<? extends OperationResult> operationResultClazz) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }
    
    public int getOpCode() {
        return opCode;
    }
    
    public Class getOperationClazz() {
        return operationClazz;
    }
    
    public Class getOperationResultClazz() {
        return operationResultClazz;
    }
    
    public static OperationType fromOpCode(final int opCode) {
        return Arrays.stream(OperationType.values()).filter(data -> data.getOpCode() == opCode).findFirst().get();
    }
    
    public static OperationType fromOperation(final Operation operation) {
        return Arrays.stream(OperationType.values()).filter(data -> data.getOperationClazz().equals(operation.getClass())).findFirst().get();
    }
}
