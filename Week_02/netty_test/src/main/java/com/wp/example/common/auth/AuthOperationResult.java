package com.wp.example.common.auth;

import com.wp.example.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {
    
    private final boolean passAuth;
    
}
