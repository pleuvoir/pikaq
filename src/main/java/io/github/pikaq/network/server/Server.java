package io.github.pikaq.network.server;

import io.github.pikaq.network.Networkable;

public interface Server extends Networkable {

	void start(ServerConfig serverConfig);
	
	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	String getServerName();
}
