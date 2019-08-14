package io.github.pikaq.remoting;

import org.junit.Test;

import io.github.pikaq.server.ServerConfig;
import io.github.pikaq.server.SimpleServer;

public class SimpleServerTest {

	@Test
	public void test() {
		SimpleServer server = new SimpleServer(new ServerConfig(8443));
		server.start();
	}
}
