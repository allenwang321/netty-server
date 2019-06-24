package com.bestboke.nettyserver.learn1;

import com.bestboke.nettycommons.DefaultParameter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer {
    // 默认绑定的端口

    public static void main(String[] args) {


        // 创建两个线程组
        // 负责监听端口
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 处理数据
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 创建引导类，引导服务端的启动
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 添加两个线程组
        serverBootstrap.group(boss, worker);

        // 指定服务端的I/O模型
        serverBootstrap.channel(NioServerSocketChannel.class);

        // 对每条连接的数据进行读写，处理业务逻辑
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel ch) {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                        System.out.println(msg);
                    }
                });
            }
        });


        // 监听一个指定端口
        serverBootstrap.bind(DefaultParameter.DEFAULT_PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口绑定成功");
            } else {
                System.out.println("端口绑定失败");
            }
        });

    }
}
