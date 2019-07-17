package io.github.pikaq.network.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.github.pikaq.network.netty.NettyServerContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServerStartTask implements Callable<NettyServerContext> {

	private final ServerConfig serverConfig;

	public NettyServerStartTask(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public NettyServerContext call() throws Exception {
		Stopwatch stopwatch = Stopwatch.createStarted();
		final ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		bootstrap.group(bossGroup, workGroup)
				.localAddress(new InetSocketAddress(serverConfig.getServerOpenPort()))
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, serverConfig.getSoBacklog())
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childOption(ChannelOption.SO_KEEPALIVE, false)
				.childOption(ChannelOption.TCP_NODELAY, true)
		 .childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				
			}
		});
		//即使是同步等待，依然可以回调
		ChannelFuture f = bootstrap.bind().sync();
		f.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				logger.info("NettyServerStartTask#operationComplete，cost：{}ms",
						stopwatch.elapsed(TimeUnit.MILLISECONDS));
			}
		});
		
		// 验证通道存活 TODO
		
		
		NettyServerContext serverContext = NettyServerContext.create()
				.channel(f.channel())
				.bossGroup(bossGroup)
				.workGroup(workGroup)
				.serverConfig(serverConfig)
				.build();
		
		return serverContext;
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());
}
