package com.wp.example.server;

import com.wp.example.server.codec.OrderFrameDecoder;
import com.wp.example.server.codec.OrderFrameEncoder;
import com.wp.example.server.codec.OrderProtocolDecoder;
import com.wp.example.server.codec.OrderProtocolEncoder;
import com.wp.example.server.codec.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ExecutionException;

public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(new NioEventLoopGroup(0, new DefaultThreadFactory("boss")));
        
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        serverBootstrap.option(NioChannelOption.SO_BACKLOG, 1024);
        
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //顺序是重要的
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OrderProtocolEncoder());
                //添加打印日志的handler
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                
                pipeline.addLast(new OrderServerProcessHandler());
            }
        });

        ChannelFuture sync = serverBootstrap.bind(8090).sync();
        
        sync.channel().closeFuture().get();
    }
    
}
