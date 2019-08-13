package io.github.pikaq.remoting;


import io.github.pikaq.server.DefaultServer;
import io.github.pikaq.server.ServerConfig;
import org.junit.Test;

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
