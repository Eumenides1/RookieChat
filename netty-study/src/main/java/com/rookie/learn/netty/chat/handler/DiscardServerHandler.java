package com.rookie.stack.io.netty.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author eumenides
 * @description
 * @date 2024/1/27
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    static Set<Channel> channelList = new HashSet<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 通知其他人，用户上线了
        channelList.forEach(e -> {
            e.writeAndFlush("[客户端]"+ctx.channel().remoteAddress() + "上线了");
        });
        channelList.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
//        Charset gbk = Charset.forName("GBK");
        System.out.println("收到数据：" + message);
//        分发给聊天室内的所有客户端
//        通知其他人 我上线了
        channelList.forEach(e->{
            if(e == ctx.channel()){
                e.writeAndFlush("[自己] ： " + message);
            }else{
                e.writeAndFlush("[客户端] " +ctx.channel().remoteAddress()+"：" + message);
            }
        });
    }

    /**
     * Channel 处于不活跃的时候会调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 通知其他客户端，该用户下线了
        channelList.remove(ctx.channel());
        channelList.forEach(e -> {
            e.writeAndFlush("[客户端]"+ctx.channel().remoteAddress() + "下线了");
        });
    }
}
