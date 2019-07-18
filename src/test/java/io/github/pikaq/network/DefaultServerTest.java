package io.github.pikaq.network;

import org.junit.Test;

import io.github.pikaq.network.server.DefaultServer;
import io.github.pikaq.network.server.ServerConfig;

public class DefaultServerTest {

	@Test
	public void test(){
		DefaultServer server = new DefaultServer("测试服务端");
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setListeningPort(8081);
		server.start(serverConfig);
	}
}
