package io.github.pikaq.network.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import io.github.pikaq.common.util.NameThreadFactoryImpl;
import io.github.pikaq.common.util.PortUtils;
import io.github.pikaq.network.HostAndPort;
import io.github.pikaq.network.NetworkLocationEnum;
import io.github.pikaq.network.ServerState;
import io.github.pikaq.network.netty.NettyServerContext;
import io.github.pikaq.network.netty.NettyServerContextHolder;

public abstract class AbstractNetworkServer implements NetworkServer {

	protected List<Thread> shutdownHooks = new ArrayList<>();

	protected ExecutorService publicExecutors;

	
	@Override
	public void start(ServerConfig serverConfig) {

		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			logger.info("{}开始启动", getServerName());
			
			publicExecutors = Executors.newFixedThreadPool(serverConfig.getPublicThreadPoolNums(),
					new NameThreadFactoryImpl("public_pools"));
			
			//支持回调的future模式
			ListeningExecutorService service = MoreExecutors.listeningDecorator(publicExecutors);
			NettyServerStartTask task = new NettyServerStartTask(serverConfig);
			
			ListenableFuture<NettyServerContext> future = service.submit(task);
			Futures.addCallback(future, new FutureCallback<NettyServerContext>() {
				@Override
				public void onSuccess(NettyServerContext serverContext) {
					
					
					NettyServerContextHolder.set(serverContext);
					
					NetworkServerContext networkServerContext = NetworkServerContext.create()
						.hostAndPort(null)
						.nettyServerContext(serverContext)
						.serverState(ServerState.RUNNING)
						.build();
						
					NetworkServerContextHolder.set(networkServerContext);
					
					// 当netty启动完成后通知业务线程继续进行
					logger.info("{}|netty启动完成，通知业务线程继续进行", getServerName());
					try {
						doStart(serverConfig, serverContext);
					} catch (Throwable e) {
						logger.error("{}|netty启动完成，通知业务线程继续进行过程出现错误，程序异常退出。", getServerName(), e);
						clearResources();
					}
				}
				@Override
				public void onFailure(Throwable t) {
					logger.error("{}启动失败回调， ", getServerName(), t);
				}
			}, MoreExecutors.directExecutor());
			
			System.out.println(1);
			//最后同步阻塞线程不退出
			future.get().getChannel().closeFuture();
			System.out.println(11);
		} catch (Throwable e) {
			logger.error("{}运行异常， ", getServerName(), e);
		} finally {
			System.out.println(111);
			clearResources();
			logger.info("{}运行结束，服务运行时长：{}ms", getServerName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
		}
	}
	

	@Override
	public void shutdown() {
		NettyServerContext nettyServerContext = NettyServerContextHolder.current();
		System.out.println("---"+nettyServerContext.getBossGroup());
		nettyServerContext.getBossGroup().shutdownGracefully();
		nettyServerContext.getWorkGroup().shutdownGracefully();
		logger.info("{} shutdown netty byebye..", getServerName());
		doClose();
	}

	protected abstract void doStart(ServerConfig serverConfig, NettyServerContext serverInfo);
	
	protected abstract void doClose();

	@Override
	public HostAndPort getHostAndPort() {
		return null;
	}

	protected void validate(ServerConfig serverConfig) throws IllegalArgumentException {
		int soBacklog = serverConfig.getSoBacklog();
		Preconditions.checkArgument(soBacklog > 0, "Socket连接缓冲队列大小配置错误，必须大于0");
		int publicThreadPoolNums = serverConfig.getPublicThreadPoolNums();
		Preconditions.checkArgument(publicThreadPoolNums > 0, "公共线程池数配置错误，必须大于0");
		int serverOpenPort = serverConfig.getServerOpenPort();
		Preconditions.checkArgument(serverOpenPort > -1, "远程服务开放端口配置错误，请设置值");
		Preconditions.checkArgument(PortUtils.checkPortAvailable(serverOpenPort), serverOpenPort + "端口被占用，请重新配置。");
	}

	@Override
	public NetworkLocationEnum networkLocation() {
		return NetworkLocationEnum.SERVER;
	}

	@Override
	public void registerShutdownHooks(Thread... hooks) {
		List<Thread> h = Arrays.asList(hooks);
		h.forEach(hook -> Runtime.getRuntime().addShutdownHook(hook));
		this.shutdownHooks.addAll(h);
	}

	
	private void clearResources() {
		AbstractNetworkServer.this.shutdown();
		NettyServerContextHolder.clear();
		NetworkServerContextHolder.clear();
	}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
}
