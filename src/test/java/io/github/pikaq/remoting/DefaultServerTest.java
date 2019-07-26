package io.github.pikaq.remoting;

import org.junit.Test;

import io.github.pikaq.remoting.server.DefaultServer;
import io.github.pikaq.remoting.server.ServerConfig;

public class DefaultServerTest {

	@Test
	public void test() {
		DefaultServer server = new DefaultServer("local_server");
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setListeningPort(8443);
		serverConfig.setSoBacklog(1000);
		server.setServerConfig(serverConfig);
		server.start();
	}
}
