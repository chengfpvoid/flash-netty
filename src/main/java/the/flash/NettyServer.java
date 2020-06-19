package the.flash;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyServer {

    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        //老板 负责接活的  这里是监听端口，accept新连接的线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        //员工 负责干活的  处理每一条连接的数据读写的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        //确定线程模型
        serverBootstrap
                .group(boosGroup, workerGroup)
                //指定NIO模型
                .channel(NioServerSocketChannel.class)
                //给NioServerSocketChannel指定自定义属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                .childAttr(clientKey, "clientValue")
                //给服务端channel设置一些属性，临时存放已完成三次握手请求队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                //childOption()可以给每条连接设置一些TCP底层相关的属性
                //ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
                //ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，
                // 通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                //定义后续每条连接的数据读写 NioSocketChannel 对NIO类型连接的抽象
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println(ch.attr(clientKey).get());
                    }
                });

        //绑定端口
        bind(serverBootstrap, BEGIN_PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        //异步方法 调用之后 立即返回的，返回值是ChannelFuture，可添加监听器 GenericFutureListener
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
