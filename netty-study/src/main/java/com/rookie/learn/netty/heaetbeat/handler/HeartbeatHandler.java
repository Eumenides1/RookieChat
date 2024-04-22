package com.rookie.learn.netty.heaetbeat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
public class HeartbeatHandler extends SimpleChannelInboundHandler<String> {

    int readTimeOut = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(" ====== > [server] message received : " + msg);
        // 如果接收到的消息是"Heartbeat Packet"，则回复"ok"
        if ("Heartbeat Packet".equals(msg)) {
            ctx.channel().writeAndFlush("ok");
        } else {
            // 如果是其他信息，打印其他信息处理 ...
            System.out.println(" 其他信息处理 ... ");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        // 定义一个字符串，用于存储超时事件的类型
        String eventType = null;
        // 根据事件的状态进行切换
        switch (event.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                // 读空闲的计数加1
                readTimeOut++;
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                // 不处理
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                // 不处理
                break;
        }

        // 打印出超时事件的信息
        System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
        // 如果读空闲的次数超过3次，则关闭连接，释放更多资源
        if (readTimeOut > 3) {
            System.out.println(" [server]读空闲超过3次，关闭连接，释放更多资源");
            ctx.channel().writeAndFlush("idle close");
            ctx.channel().close();
        }
    }

    // 当通道激活时，即连接成功建立时，该方法将被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("=== " + ctx.channel().remoteAddress() + " is active ===");
    }


}
