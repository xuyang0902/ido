package com.ido.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;


/**
 * netty官方的源码分析 还是蛮不错的
 * https://netty.io/wiki/related-articles.html
 *
 * @author xu.qiang
 * @date 18/11/13
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new MyThreadFactory("Boss-"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(4, new MyThreadFactory("Worker-"));

        //woker线程 构建socketchannel时候，通信必须走的一层chandler
        ChannelInitializer<SocketChannel> channelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("encode", new StringEncoder());
                pipeline.addLast("decode", new StringDecoder());
                pipeline.addLast("handler", new NettyServerHandler());
            }
        };

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ServerBootstrap handler = serverBootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 4096)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.SO_SNDBUF, 65536)
                .option(ChannelOption.SO_RCVBUF, 65536)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer);

        handler.bind(new InetSocketAddress("127.0.0.1", 9999)).sync();

    }

    static class NettyServerHandler extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("threadName:" + Thread.currentThread().getName() + "   客户端注册上来了");
            super.channelRegistered(ctx);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

            System.out.println("threadName:" + Thread.currentThread().getName() + "   服务端接受到消息-->" + msg.toString());

            ctx.channel().writeAndFlush("你好啊 客户端小姐姐");


        }

    }
}
