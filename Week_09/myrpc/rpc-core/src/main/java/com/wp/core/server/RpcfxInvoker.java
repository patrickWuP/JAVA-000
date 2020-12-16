package com.wp.core.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wp.core.api.RpcfxResolver;
import com.wp.core.bean.MyRpcRequest;
import com.wp.core.bean.MyRpcResponse;
import com.wp.core.exception.MyRpcException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcfxInvoker {
    
    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public MyRpcResponse invoke(MyRpcRequest request) {
        MyRpcResponse response = new MyRpcResponse();
        String serviceClass = request.getKclass();
        // 作业1：改成泛型和反射
        Object resolve = resolver.resolve(serviceClass);

        try {
            Method method = resolve.getClass().getDeclaredMethod(request.getMethod(), request.getParamTypes());
            for (int i = 0; i < request.getParamTypes().length; i++) {
                //类型不匹配，则进行类型转换
                if (!request.getParamTypes()[i].isInstance(request.getParams()[i])) {
                    Constructor constructor = request.getParamTypes()[0].getConstructor(String.class);
                    request.getParams()[i] = constructor.newInstance(request.getParams()[i].toString());
                }
            }
            Object invoke = method.invoke(resolve, request.getParams());
            response.setResult(JSON.toJSONString(invoke, SerializerFeature.WriteClassName));
            response.setStatus(100);
            return response;
        } catch ( NoSuchMethodException | IllegalAccessException | InstantiationException |InvocationTargetException e) {
            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setE(new MyRpcException("server invoke failed", e));
            response.setStatus(999);
            return response;
        }
    }
}
