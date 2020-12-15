package com.wp.core.bean;

public class MyRpcResponse {
    
    //响应结果
    private Object result;
    
    //响应状态,100为成功，999为异常
    private Integer status;

    //响应异常
    private Throwable e;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Throwable getE() {
        return e;
    }

    public void setE(Throwable e) {
        this.e = e;
    }
}
