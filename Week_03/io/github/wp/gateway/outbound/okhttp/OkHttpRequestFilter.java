package io.github.wp.gateway.outbound.okhttp;

import io.github.wp.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class OkHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        System.out.println("OkHttpRequestFilter 过滤某些特殊请求");
    }
}
