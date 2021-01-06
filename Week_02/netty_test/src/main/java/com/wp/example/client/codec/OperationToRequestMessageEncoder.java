package com.wp.example.client.codec;

import com.wp.example.common.Operation;
import com.wp.example.common.RequestMessage;
import com.wp.example.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Operation msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(), msg);
        
        out.add(requestMessage);
    }
}
