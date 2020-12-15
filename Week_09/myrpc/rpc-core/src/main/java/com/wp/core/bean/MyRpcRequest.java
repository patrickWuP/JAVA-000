package com.wp.core.bean;

public class MyRpcRequest {
    //请求方法
    private String method;
    //请求的类
    private String kclass;
    //请求的参数
    private Object[] params;
    //请求的参数类型
    private Class[] paramTypes;
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKclass() {
        return kclass;
    }

    public void setKclass(String kclass) {
        this.kclass = kclass;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }
}
