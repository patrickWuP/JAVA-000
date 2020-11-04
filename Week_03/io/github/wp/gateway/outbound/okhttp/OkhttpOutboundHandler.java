package io.github.wp.gateway.outbound.okhttp;

import io.github.wp.gateway.outbound.httpclient4.NamedThreadFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import okhttp3.*;
import okhttp3.internal.http2.Header;

import java.io.IOException;
import java.util.concurrent.*;

public class OkhttpOutboundHandler {
    
    private String backendUrl;
    
    private ExecutorService proxyService;
    
    private OkHttpClient okHttpClient;
    
    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        
        proxyService = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize), 
                new NamedThreadFactory("proxyService"), handler);
        
        okHttpClient = new OkHttpClient();
    }
    
    public void handle(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + fullHttpRequest.uri();
        proxyService.submit(() -> fetchGet(fullHttpRequest, ctx, url));
    }
    
    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        Request build = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(inbound, ctx, response);
            }
        });
    }
    
    private void handleResponse(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, final Response response) {
        FullHttpResponse fullHttpResponse = null;
        try {
            byte[] body = response.body().bytes();
            
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(body));
            fullHttpResponse.headers().set("Content-Type", "application/json");
        
        } catch (IOException e) {
            e.printStackTrace();
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();
        }
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
