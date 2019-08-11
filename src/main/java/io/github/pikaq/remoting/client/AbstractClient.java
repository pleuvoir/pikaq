package io.github.pikaq.remoting.client;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.github.pikaq.PikaqConst;
import io.github.pikaq.common.util.MixUtils;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initializer;
import io.github.pikaq.remoting.Pendings;
import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.RemoteExceptionTranslator;
import io.github.pikaq.remoting.RemoteSendException;
import io.github.pikaq.remoting.RemotingContext;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.protocol.codec.RemoteCommandCodecHandler;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class AbstractClient implements Client {

	private Bootstrap bootstrap;
	private NioEventLoopGroup eventLoopGroup;
	private ClientConfig clientConfig;
	private volatile Channel channel;
	private volatile RunningState runningState = RunningState.WAITING;
	private ConnnectManager connnectManager = ConnnectManager.INSTANCE;

	
	AbstractClient() {
		Initializer.init();
	}

	@Override
	public void connect() {
		
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		logger.info("[{}]开启连接", getClientName());
		
		bootstrap = new Bootstrap();
		eventLoopGroup = new NioEventLoopGroup();
		
			bootstrap.group(eventLoopGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeoutMillis())
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4));
					ch.pipeline().addLast(RemoteCommandCodecHandler.INSTANCE);
					ch.pipeline().addLast(new IdleStateHandler(30, 0, 0,TimeUnit.SECONDS)); //30秒没有读事件
					ch.pipeline().addLast(new HealthyChecker(AbstractClient.this)); 
					ch.pipeline().addLast(SingletonFactoy.get(ClientRemoteCommandtDispatcher.class));
				}
			});
			
		InetSocketAddress remoteAddress = new InetSocketAddress(clientConfig.getHost(), clientConfig.getPort());

		ChannelFuture future = doConnectWithRetry(remoteAddress, clientConfig.getStartFailReconnectTimes());

		channel = future.channel();

		RemotingContext remotingContext = RemotingContext.create()
				.channel(channel)
				.clientConfig(clientConfig)
				.build();
		
		connnectManager.putChannel(channel);
		connnectManager.fireHoldTask();
		connnectManager.printAliveChannel();

		doStart(remotingContext);

		runningState = RunningState.RUNNING;
		logger.info("[{}]客户端连接启动完成，耗时：{}ms", getClientName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
	}

	@Override
	public void shutdown() {
		logger.info("[{}]shutdown byebye..", getClientName());
		if (eventLoopGroup != null) {
			eventLoopGroup.shutdownGracefully();
		}
		doClose();
		runningState = RunningState.WAITING;
	}
	
	protected abstract void doClose();
	
	protected abstract void doStart(RemotingContext remotingContext);
	
	protected ChannelFuture doConnectWithRetry(InetSocketAddress remoteAddress, int retryTimes)
			 {
		ChannelFuture future = bootstrap.connect(remoteAddress);
		future.awaitUninterruptibly(); //connect不可以使用sync会报错
		if (future.isSuccess()) {
			ChannelFuture f = (ChannelFuture) future;
			logger.info("[{}]客户端已连接远程节点：[{}]", getClientName(), f.channel().remoteAddress());
			return f;
		} else if (retryTimes == 0) {
			logger.error("[{}]客户端连接远程节点{}：{}尝试重连到达上限，不再进行连接。原因：{}", getClientName(), clientConfig.getHost(),
					clientConfig.getPort(), future.cause().getMessage());
			throw new RemoteClientException(future.cause().getMessage());
		} else {
			// 第几次重连
			int sequence = clientConfig.getStartFailReconnectTimes() - retryTimes + 1;
			int delay = 1 << sequence;
			logger.warn("[{}]客户端连接远程节点第{}次连接失败，{} {}秒后尝试重试。", getClientName(), sequence, future.cause().getMessage(),
					delay);
			MixUtils.sleep(delay);
			return doConnectWithRetry(remoteAddress, retryTimes - 1);
		}
	}
	

	public void setClientConfig(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public ClientConfig getClientConfig() {
		return clientConfig;
	}


	@Override
	public RunningState runningState() {
		return runningState;
	}

	@Override
	public void sendOneWay(RemotingCommand request) throws RemoteSendException {
		checkRunningState();
		RemoteCommandLifeCycleListener commandLifeCycleListener = SingletonFactoy.get(RemoteCommandLifeCycleListener.class);
		commandLifeCycleListener.beforeSend(request);
		this.channel.writeAndFlush(request);
		commandLifeCycleListener.afterSend(request);
	}

	@Override
	public RemotingCommand sendRequest(RemotingCommand request) throws RemoteSendException {
		CompletableFuture<RemotingCommand> promise = this.sendAsyncRequest(request);
		RemotingCommand result = null;
		try {
			result = promise.get(PikaqConst.DEFAULT_SEND_TIMEOUT_MS, TimeUnit.MILLISECONDS);
		} catch (Throwable e) {
			throw RemoteExceptionTranslator.convertRemoteException(e);
		}
		return result;
	}

	@Override
	public CompletableFuture<RemotingCommand> sendAsyncRequest(RemotingCommand request) throws RemoteSendException {
		
		checkRunningState();
		
		RemoteCommandLifeCycleListener commandLifeCycleListener = SingletonFactoy.get(RemoteCommandLifeCycleListener.class);
		commandLifeCycleListener.beforeSend(request);
		
		CompletableFuture<RemotingCommand> promise = new CompletableFuture<RemotingCommand>();
		
		//占位，保存请求记录，promise将在另外一个线程中complete，或者在下面exception
		Pendings.put(request.getMessageId(), promise);
		
		this.channel.writeAndFlush(request).addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> f) throws Exception {
				
				commandLifeCycleListener.sendComplete(request);
				
				if (!f.isSuccess()) {
					commandLifeCycleListener.sendException(request, f.cause());
					//异常时移除请求记录，并设置为失败
					CompletableFuture<RemotingCommand> prev = Pendings.remove(request.getMessageId());
					if (prev != null) {
						prev.completeExceptionally(f.cause());
					}
				}
			}
		});
		
		commandLifeCycleListener.afterSend(request);
		return promise;
	}
	
	
	private void checkRunningState() throws RemoteSendException{
		if (!runningState.isRunning()) {
			logger.debug("客户端未连接，请连接后再发送。");
			throw new RemoteSendException("客户端未连接，请连接后再发送。");
		}
		if (!connnectManager.validate(channel)) {
			throw new RemoteSendException("连接通道不可用");
		}
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
