package io.github.pikaq.network;

import java.util.concurrent.TimeUnit;

import io.github.pikaq.network.netty.NettyServerContext;
import io.github.pikaq.network.server.AbstractNetworkServer;
import io.github.pikaq.network.server.NetworkServerContext;
import io.github.pikaq.network.server.ServerConfig;
import io.netty.channel.Channel;

public class NetWorkServer extends AbstractNetworkServer {


	@Override
	public HostAndPort getHostAndPort() {
		return null;
	}

	@Override
	protected void doStart(ServerConfig serverConfig, NettyServerContext serverInfo) {

		Channel channel = serverInfo.getChannel();
		System.out.println("channel.isActive=" + channel.isActive());
		System.out.println("channel.isOpen=" + channel.isOpen());
//		try {
//			TimeUnit.SECONDS.sleep(1);
//			throw new RuntimeException("启动运行时异常");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public String getServerName() {
		return "测试服务端";
	}

	@Override
	protected void doClose() {
		System.out.println("close.....");
	}

}
