package com.wp.example.common.keepalive;

import com.wp.example.common.OperationResult;
import lombok.Data;

@Data
public class KeepAliveOperationResult extends OperationResult {
    
    private final long time;
    
}
