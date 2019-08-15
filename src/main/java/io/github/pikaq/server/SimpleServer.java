package io.github.pikaq.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.pikaq.RemotingAbstract;
import io.github.pikaq.common.util.RemotingUtils;
import io.github.pikaq.protocol.codec.RemoteCommandCodecHandler;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
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
		this.bootstrap = new ServerBootstrap();
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
						ch.pipeline().addLast(new NettyServerHandler());
					}
				});
	}

	@Override
	public boolean start() {
		try {
			ChannelFuture f = bootstrap.bind().sync();
			logger.info("服务已启动，监听端口：{}", serverConfig.getListeningPort());
			return f.isSuccess();
		} catch (Throwable e) {
			logger.error("服务启动失败", e);
			return false;
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

}
