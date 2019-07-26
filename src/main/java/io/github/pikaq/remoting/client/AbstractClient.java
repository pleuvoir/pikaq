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

	private Bootstrap bootstrap;
	private NioEventLoopGroup eventLoopGroup;
	private ClientConfig clientConfig;
	private Waiter retryWaiter = new Waiter();
	private ConnnectManager connnectManager = ConnnectManager.INSTANCE;


	@Override
	public void start() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		logger.info("[{}]开启连接", getClientName());
		
		bootstrap = new Bootstrap();
		eventLoopGroup = new NioEventLoopGroup();
		
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
			
			ChannelFuture cf = doConnectWithRetry(remoteAddress, clientConfig.getStartFailReconnectTimes());
			
			//因为重试连接失败会触发close事件，所以需要在此手动等待返回结果
			retryWaiter.on();
			
			Channel channel = cf.channel();
			
			RemotingContext remotingContext = RemotingContext.create()
					.channel(channel)
					.clientConfig(clientConfig)
					.build();
			RemotingContextHolder.set(remotingContext);
			
			connnectManager.putChannel(channel);
			connnectManager.fireHoldTask();
			connnectManager.printAliveChannel();
			
			doStart(remotingContext);
			
			logger.info("[{}]客户端连接启动完成，耗时：{}ms", getClientName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
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
	
	protected abstract void doStart(RemotingContext remotingContext);
	
	
	protected ChannelFuture doConnectWithRetry(InetSocketAddress remoteAddress, int retryTimes) {
		ChannelFuture cf = bootstrap.connect(remoteAddress);
		cf.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()) {
					ChannelFuture f = (ChannelFuture) future;
					logger.info("[{}]客户端已连接远程节点：[{}]", getClientName(), f.channel().remoteAddress());
					retryWaiter.off();
				} else if (retryTimes == 0) {
					logger.error("[{}]客户端连接远程节点{}：{}尝试重连到达上限，不再进行连接。原因：{}", getClientName(), clientConfig.getHost(),
							clientConfig.getPort(), future.cause().getMessage());
					retryWaiter.off();
				} else {
					// 第几次重连
					int sequence = clientConfig.getStartFailReconnectTimes() - retryTimes + 1;
					int delay = 1 << sequence;
					logger.warn("[{}]客户端连接远程节点第{}次连接失败，{} {}秒后尝试重试。", getClientName(), sequence,
							future.cause().getMessage(), delay);
					bootstrap.config().group().schedule(() -> {
						doConnectWithRetry(remoteAddress, retryTimes - 1);
					}, delay, TimeUnit.SECONDS);
				}
			}
		});
		return cf;
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
