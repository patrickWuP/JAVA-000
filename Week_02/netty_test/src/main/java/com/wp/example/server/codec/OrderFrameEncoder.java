package com.wp.example.server.codec;

import io.netty.handler.codec.LengthFieldPrepender;

public class OrderFrameEncoder extends LengthFieldPrepender {

    public OrderFrameEncoder() {
        super(2);
    }
}
