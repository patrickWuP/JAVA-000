package com.wp.example.client;

import com.wp.example.client.codec.*;
import com.wp.example.client.codec.dispatcher.OperationResultFuture;
import com.wp.example.client.codec.dispatcher.RequestPendingCenter;
import com.wp.example.client.codec.dispatcher.ResponseDispatcherHandler;
import com.wp.example.common.OperationResult;
import com.wp.example.common.RequestMessage;
import com.wp.example.common.order.OrderOperation;
import com.wp.example.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class ClientV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.channel(NioSocketChannel.class);
        //连接超时
        bootstrap.option(NioChannelOption.CONNECT_TIMEOUT_MILLIS, 10 * 1000);
        bootstrap.group(new NioEventLoopGroup());
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //顺序是重要的
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());
                
                pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));
                pipeline.addLast(new OperationToRequestMessageEncoder());
                //添加打印日志的handler
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture sync = bootstrap.connect("127.0.0.1", 8090);

        //保证先连接上了
        sync.sync();
        long streamId = IdUtil.nextId();

        RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "tudou"));

        OperationResultFuture operationResultFuture = new OperationResultFuture();
        
        requestPendingCenter.add(streamId, operationResultFuture);
        //不见得已经连接上了
        sync.channel().writeAndFlush(requestMessage);

        OperationResult result = operationResultFuture.get();
        System.out.println(result);
        sync.channel().closeFuture().get();
    }
    
}
