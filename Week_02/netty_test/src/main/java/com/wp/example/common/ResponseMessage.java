package com.wp.example.common;

public class ResponseMessage extends Message<OperationResult> {
    
    public Class<OperationResult> getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationResultClazz();
    }
    
}
