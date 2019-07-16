package io.github.pikaq.network.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import io.github.pikaq.common.util.Waiter;
import io.github.pikaq.network.NetworkLocationEnum;

public abstract class AbstractNetworkServer implements NetworkServer {

	protected List<Thread> shutdownHooks = new ArrayList<>();

	@Override
	public void start(ServerConfig serverConfig) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			logger.info("{}开始启动", getServerName());
			this.validate(serverConfig);
			this.doStart(serverConfig);
			logger.info("{}启动完成，耗时：{}ms", getServerName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
			Waiter.create().waitUntilDead();
		} catch (Throwable e) {
			logger.error("{}运行异常， ", getServerName(), e);
		} finally {
			logger.info("{}运行结束，服务运行时长：{}ms", getServerName(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
		}
	}

	protected abstract void validate(ServerConfig serverConfig) throws IllegalArgumentException;

	protected abstract void doStart(ServerConfig serverConfig);

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

	private final Logger logger = LoggerFactory.getLogger(getClass());
}
