package io.github.pikaq.remoting;

import io.github.pikaq.server.ServerConfig;
import io.github.pikaq.server.SimpleServer;

public class SimpleServerTest {


	public static void main(String[] args) {
		SimpleServer server = new SimpleServer(new ServerConfig(8443));
		boolean start = server.start();
		if (start) {
			System.out.println("start success.");
		}
		
		System.out.println("-=-=-=-=-=-=-=");
	}

}
