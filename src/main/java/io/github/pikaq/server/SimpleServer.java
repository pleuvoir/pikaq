package io.github.pikaq.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import io.github.pikaq.ClientChannelInfoManager;
import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RemotingAbstract;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.common.util.RemotingUtils;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.initialization.support.Initializer;
import io.github.pikaq.protocol.codec.RemoteCommandCodecHandler;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SimpleServer extends RemotingAbstract implements RemotingServer {

	protected List<Thread> shutdownHooks = new ArrayList<>();
	protected EventLoopGroup bossGroup;
	protected EventLoopGroup workGroup;
	private ServerConfig serverConfig;
	private ServerBootstrap bootstrap;


	public SimpleServer(ServerConfig serverConfig){
		RemotingUtils.validate(serverConfig);
		this.serverConfig = serverConfig;
		Initializer.init();
		final ServerBootstrap bootstrap = new ServerBootstrap();
		this.bossGroup = new NioEventLoopGroup();
		this.workGroup = new NioEventLoopGroup();

		bootstrap.group(bossGroup, workGroup).localAddress(new InetSocketAddress(serverConfig.getListeningPort()))
				.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, serverConfig.getSoBacklog())
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4));
						ch.pipeline().addLast(RemoteCommandCodecHandler.INSTANCE);
						ch.pipeline().addLast(new ServerIdleStateHandler(serverConfig.getAllIdleTime()));
					//	ch.pipeline().addLast(SingletonFactoy.get(ServerRemoteCommandtDispatcher.class));
						ch.pipeline().addLast(new NettyServerHandler());
					}
				});
	}

	@Override
	public void start() {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			ChannelFuture f = bootstrap.bind().sync();
			logger.info("服务已启动，监听端口：{}", serverConfig.getListeningPort());
			// 最后同步阻塞线程不退出
			f.channel().closeFuture().sync();
		} catch (Throwable e) {
			logger.error("服务启动失败", e);
		} finally {
			logger.info("服务即将关闭，服务运行：cost：{}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
			shutdown();
		}
	}

	@Override
	public void shutdown() {
		logger.info("shutdown byebye..");
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
			bossGroup = null;
		}
		if (workGroup != null) {
			workGroup.shutdownGracefully();
			workGroup = null;
		}
	}

	@Override
	public void registerShutdownHooks(Thread... hooks) {
		List<Thread> h = Arrays.asList(hooks);
		h.forEach(hook -> Runtime.getRuntime().addShutdownHook(hook));
		this.shutdownHooks.addAll(h);
	}

	@Override
	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	/**
	 * 处理服务端收到的请求
	 */
	class NettyServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg) throws Exception {
			logger.info(" -=-=-=-=-= processMessageReceived -=-=-=-=-= {}", msg.toJSON());
			processMessageReceived(ctx, msg);
		}
	}
	
	@Override
	public RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
			throws RemotingTimeoutException, RemotingSendRequestException {
		//TODO 
		Channel channel = SingletonFactoy.get(ClientChannelInfoManager.class).get(null).getChannel();
		return super.invokeSyncImpl(channel, request, timeoutMillis);
	}

	@Override
	public void invokeAsync(RemotingCommand request, InvokeCallback invokeCallback) throws RemotingSendRequestException {
		Channel channel = SingletonFactoy.get(ClientChannelInfoManager.class).get(null).getChannel();
		super.invokeAsyncImpl(channel, request, invokeCallback);
	}

	@Override
	public void invokeOneway(RemotingCommand request) throws RemotingSendRequestException {
		Channel channel = SingletonFactoy.get(ClientChannelInfoManager.class).get(null).getChannel();
		super.invokeOnewayImpl(channel, request);
	}

}
