package com.wp.core.exception;

public class MyRpcException extends Exception {

    public MyRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyRpcException(String message) {
        super(message);
    }
}
