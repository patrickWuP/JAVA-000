package com.wp.example.client;

import com.wp.example.client.codec.*;
import com.wp.example.common.Operation;
import com.wp.example.common.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class ClientV1 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.group(new NioEventLoopGroup());

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //顺序是重要的
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());
                
                pipeline.addLast(new OperationToRequestMessageEncoder());
                //添加打印日志的handler
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture sync = bootstrap.connect("127.0.0.1", 8090);

        //new OrderOperation 转换的工作
//        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"));
//        
        Operation requestMessage = new OrderOperation(1001, "tudou");
        //保证先连接上了
        sync.sync();
        //不见得已经连接上了
        sync.channel().writeAndFlush(requestMessage);
        
        sync.channel().closeFuture().get();
    }
    
}
