package com.wp.core.api;

public interface RpcfxResolver <T> {
    
    T resolve(String serviceClass);
}
