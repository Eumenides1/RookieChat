package com.rookie.im.core.server;

import com.rookie.im.core.codec.MessageDecoder;
import com.rookie.im.core.handler.RookieServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author eumenides
 * @description
 * @date 2024/4/22
 */
public class ImServer {
    private int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    public ImServer() {
        loadConfig();
        // 可选: 指定线程数量
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        start();
    }

    private void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 10240)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 配置Channel处理器
                            ChannelPipeline pipeline = ch.pipeline();
//                            // websocket 基于http协议，所以要有http编解码器
//                            pipeline.addLast("http-codec", new HttpServerCodec());
//                            // 对写大数据流的支持
//                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
//                            // 几乎在netty中的编程，都会使用到此hanler
//                            pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
                            // pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(new RookieServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            serverChannel = future.channel();
            // 添加JVM关闭钩子以优雅关闭Netty
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            // 等待服务器通道关闭，这将阻塞
            serverChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            shutdown();
        }
    }

    private void loadConfig() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            port = Integer.parseInt(prop.getProperty("tcp.port"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void shutdown() {
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
