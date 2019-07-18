package io.github.pikaq.remoting;

import org.junit.Test;

import io.github.pikaq.remoting.server.DefaultServer;
import io.github.pikaq.remoting.server.ServerConfig;

public class DefaultServerTest {

	@Test
	public void test(){
		DefaultServer server = new DefaultServer("默认服务端");
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setListeningPort(8081);
		serverConfig.setSoBacklog(1000);
		server.start(serverConfig);
	}
}
