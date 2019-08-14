package io.github.pikaq.remoting;


import io.github.pikaq.server.SimpleServer;
import io.github.pikaq.server.ServerConfig;
import org.junit.Test;

public class DefaultServerTest {

	@Test
	public void test() {
		SimpleServer server = new SimpleServer("local_server");
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.setListeningPort(8443);
		serverConfig.setSoBacklog(1000);
		server.setServerConfig(serverConfig);
		server.start();
	}
}
