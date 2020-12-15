package com.wp.rpcserver.controller;

import com.wp.core.api.RpcfxResolver;
import com.wp.core.bean.MyRpcRequest;
import com.wp.core.bean.MyRpcResponse;
import com.wp.core.server.RpcfxInvoker;
import com.wp.rpcserver.util.DemoResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OneEntranceController {
    
    @Autowired
    RpcfxInvoker rpcfxInvoker;
    
    @RequestMapping("/")
    public MyRpcResponse request(@RequestBody MyRpcRequest request) {
        return rpcfxInvoker.invoke(request);
    }
    
    @Bean
    public RpcfxInvoker createRpcfxInvoker(@Autowired RpcfxResolver rpcfxResolver){
        return new RpcfxInvoker(rpcfxResolver);
    }
    
    @Bean
    public RpcfxResolver createRpcfxResolver() {
        return new DemoResolver();
    }
}
