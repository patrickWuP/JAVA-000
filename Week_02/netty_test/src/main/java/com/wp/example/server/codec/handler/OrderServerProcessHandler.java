package com.wp.example.server.codec.handler;

import com.wp.example.common.Operation;
import com.wp.example.common.OperationResult;
import com.wp.example.common.RequestMessage;
import com.wp.example.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//帮助我自动释放ByteBuf
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        Operation operation = msg.getMessageBody();
        OperationResult result = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageBody(result);
        responseMessage.setMessageHeader(msg.getMessageHeader());
        
        ctx.writeAndFlush(responseMessage);
    }
}
