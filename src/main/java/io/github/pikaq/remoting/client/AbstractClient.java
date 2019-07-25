package io.github.pikaq.remoting.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.github.pikaq.common.util.Waiter;
import io.github.pikaq.remoting.RemoteLocationEnum;
import io.github.pikaq.remoting.RemotingContext;
import io.github.pikaq.remoting.RemotingContextHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class AbstractClient implements Client {

	private NioEventLoopGroup eventLoopGroup;
	private ClientConfig clientConfig;

	@Override
	public void connect() {

		Stopwatch stopwatch = Stopwatch.createStarted();
		
		logger.info("[{}]开始启动", getClientName());
		
		final Bootstrap bootstrap = new Bootstrap();
		eventLoopGroup = new NioEventLoopGroup();
		
		try {
			bootstrap.group(eventLoopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeoutMillis())
			.option(ChannelOption.SO_KEEPALIVE, false)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					
				}
			});
			
			InetSocketAddress remoteAddress = new InetSocketAddress(clientConfig.getHost(), clientConfig.getPort());
			
			Waiter waiter = new Waiter();
			ChannelFuture cf = bootstrap.connect(remoteAddress).addListener(new GenericFutureListener<Future<? super Void>>(){
				@Override
						public void operationComplete(Future<? super Void> future) throws Exception {
							if (future.isSuccess()) {

								ChannelFuture f = (ChannelFuture) future;
								RemotingContext remotingContext = RemotingContext.create()
										.channel(f.channel())
										.clientConfig(clientConfig)
										.build();
								RemotingContextHolder.set(remotingContext);
								
								logger.info("[{}]客户端已连接远程节点：[{}：{}]", getClientName(), clientConfig.getHost(),
										clientConfig.getPort());
								waiter.off();

							} else {
								logger.warn("[{}]客户端连接远程节点{}：{}失败，{}", getClientName(), clientConfig.getHost(),
										clientConfig.getPort(), future.cause().getMessage());
								waiter.off();
							}
						}
					});
			
			//阻塞用于多次重试连接
			waiter.on();
			
			cf.channel().closeFuture().sync();
		} catch (Throwable e) {
			logger.error("[{}]客户端连接远程节点错误：[{}：{}]", getClientName(), clientConfig.getHost(),
					clientConfig.getPort(), e);
		}finally {
			logger.info("[{}]客户端连接即将关闭，服务运行：cost：{}ms", getClientName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
			shutdown();
		}
	}

	@Override
	public void shutdown() {
		logger.info("[{}]shutdown byebye..", getClientName());
		if (eventLoopGroup != null) {
			eventLoopGroup.shutdownGracefully();
		}
		RemotingContextHolder.clear();
		doClose();
	}
	
	protected abstract void doClose();
	
	
	@Override
	public void disconnect() {
		Channel channel = RemotingContextHolder.current().getChannel();
		//TODO 工具类验证通道活跃等
		channel.close();
	}

	@Override
	public void reconnect() {
		
	}

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public ClientConfig getClientConfig() {
		return clientConfig;
	}

	@Override
	public RemoteLocationEnum remoteLocation() {
		return RemoteLocationEnum.CLIENT;
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
