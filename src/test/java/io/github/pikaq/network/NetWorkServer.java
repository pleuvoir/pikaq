package io.github.pikaq.network;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import io.github.pikaq.network.server.AbstractNetworkServer;
import io.github.pikaq.network.server.NetworkServerContext;
import io.github.pikaq.network.server.ServerConfig;

public class NetWorkServer extends AbstractNetworkServer {

	@Override
	public void shutdown(NetworkServerContext networkContext) {

	}

	@Override
	public HostAndPort getHostAndPort() {
		return null;
	}

	@Override
	protected void validate(ServerConfig serverConfig) throws IllegalArgumentException {
		int soBacklog = serverConfig.getSoBacklog();
		Preconditions.checkArgument(soBacklog > 0, "Socket连接缓冲队列大小配置错误，必须大于0");
	}

	@Override
	protected void doStart(ServerConfig serverConfig) {
		try {
			TimeUnit.SECONDS.sleep(3);
			throw new RuntimeException("启动运行时异常");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getServerName() {
		return "测试服务端";
	}

}
