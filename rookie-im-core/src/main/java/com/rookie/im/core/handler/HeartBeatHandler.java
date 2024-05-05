package com.rookie.im.core.handler;

import com.rookie.im.core.constant.Constants;
import com.rookie.im.core.utils.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eumenides
 * @description
 * @date 2024/5/5
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private Long heartBeatTime;

    public HeartBeatHandler(Long heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE){
                // 写空闲，发送心跳包
                log.info("写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // 所有事件都为空闲时，关闭连接
                Long lastReadTime = (Long) ctx.channel()
                        .attr(AttributeKey.valueOf(Constants.ReadTime)).get();
                Long now = System.currentTimeMillis();

                if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
                    //离线逻辑
                    SessionSocketHolder.offlineUserSession((NioSocketChannel) ctx.channel());
                }
            }
        }
    }
}
