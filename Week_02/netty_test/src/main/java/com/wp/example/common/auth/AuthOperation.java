package com.wp.example.common.auth;

import com.wp.example.common.Operation;
import com.wp.example.common.OperationResult;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class AuthOperation extends Operation {
    
    private final String userName;
    private final String password;
    
    public OperationResult execute() {
        if ("admin".equalsIgnoreCase(this.userName)) {
            AuthOperationResult authOperationResult = new AuthOperationResult(true);
            return authOperationResult;
        }
        
        return new AuthOperationResult(false);
    }
}
