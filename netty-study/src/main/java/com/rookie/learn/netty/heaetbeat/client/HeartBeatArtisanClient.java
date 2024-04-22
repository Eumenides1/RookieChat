package com.rookie.learn.netty.heaetbeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;

/**
 * @author eumenides
 * @description
 * @date 2024/4/22
 */
public class HeartBeatArtisanClient {
    public static void main(String[] args) throws Exception {
        // 创建一个NioEventLoopGroup，用于处理Netty的事件循环
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            // 创建一个Bootstrap对象，用于配置客户端
            Bootstrap bootstrap = new Bootstrap();
            // 设置EventLoopGroup和Channel类型
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    // 添加ChannelInitializer，用于初始化ChannelPipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取ChannelPipeline并添加解码器、编码器和心跳处理handler
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new HeartBeatArtisanClientHandler());
                        }
                    });
            // 打印出Netty客户端启动信息
            System.out.println("netty client start。。");
            // 连接到服务器，并获取Channel对象
            Channel channel = bootstrap.connect("127.0.0.1", 9001).sync().channel();
            // 定义要发送的心跳包文本
            String text = "Heartbeat Packet";
            // 创建一个Random对象，用于生成随机数
            Random random = new Random();
            // 在循环中随机发送心跳包
            while (channel.isActive()) {
                int num = random.nextInt(8);
                Thread.sleep(num * 1000);
                channel.writeAndFlush(text);
            }
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 优雅地关闭EventLoopGroup，释放资源
            eventLoopGroup.shutdownGracefully();
        }
    }
}
