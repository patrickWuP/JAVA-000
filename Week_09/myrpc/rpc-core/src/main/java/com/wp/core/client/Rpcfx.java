package com.wp.core.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.wp.core.bean.MyRpcRequest;
import com.wp.core.bean.MyRpcResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class Rpcfx {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");
    
    static {
        ParserConfig.getGlobalInstance().addAccept("com.wp");
    }
    
    public static <T> T createInstance(final Class<T> serviceClass, final String url ) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new RpcInvocationHandler(url, serviceClass));
    }
    
    private static class RpcInvocationHandler implements InvocationHandler {
        private String url;
        
        private Class serviceClass;

        public RpcInvocationHandler(String url, Class serviceClass) {
            this.url = url;
            this.serviceClass = serviceClass;
        }

        // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
        // int byte char float double long bool
        // [], data class
        
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return handlerAop(this.serviceClass.getName(), method.getName(), args, url);
        }

    }
    
    private static MyRpcResponse post(MyRpcRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);

        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String response = okHttpClient.newCall(build).execute().body().string();
        System.out.println("response json: " + response);
        return JSON.parseObject(response, MyRpcResponse.class);
    }
    
    public static Object handlerAop(String className, String methodName, Object[] args, String url) throws Throwable {
        MyRpcRequest request = new MyRpcRequest();
        request.setKclass(className);
        request.setMethod(methodName);
        request.setParams(args);
        Class[] paramTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }
        request.setParamTypes(paramTypes);
        MyRpcResponse response = post(request, url);

        // 这里判断response.status，处理异常
        if (Objects.equals(response.getStatus(), 999)) {
            throw response.getE();
        }
        // 考虑封装一个全局的RpcfxException
        return JSON.parse(response.getResult().toString());
    }
    
}
