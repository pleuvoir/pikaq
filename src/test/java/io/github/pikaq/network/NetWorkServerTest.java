package io.github.pikaq.network;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.network.server.ServerConfig;

public class NetWorkServerTest {

	@Test
	public void test() {
		NetWorkServer netWorkServer = new NetWorkServer();

		Assert.assertEquals(netWorkServer.networkLocation(), NetworkLocationEnum.SERVER);

		netWorkServer.registerShutdownHooks(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("first hook");
			}
		}));

		netWorkServer.registerShutdownHooks(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("second hook");
			}
		}));

		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setSoBacklog(2048);
		netWorkServer.start(serverConfig);
	}
}
