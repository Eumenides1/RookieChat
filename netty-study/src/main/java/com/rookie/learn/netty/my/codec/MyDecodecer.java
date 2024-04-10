package com.rookie.stack.io.netty.my.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/1/27
 */
public class MyDecodecer extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int widx = in.readableBytes();
        if (in.readableBytes() < 4) {
            return;
        }
        // 数据长度
        int i = in.readInt();
        if (in.readableBytes() < i) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[i];
        in.readBytes(data);
        System.out.println(new String(data));
        in.markReaderIndex();
    }
}
