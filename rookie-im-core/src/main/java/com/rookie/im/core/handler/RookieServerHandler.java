package com.rookie.im.core.handler;

import com.rookie.im.core.codec.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author eumenides
 * @description
 * @date 2024/4/23
 */
public class RookieServerHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println(msg.toString());
    }
}
