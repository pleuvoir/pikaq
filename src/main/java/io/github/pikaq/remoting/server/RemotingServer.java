package io.github.pikaq.remoting.server;

import io.github.pikaq.remoting.RemotingService;

public interface RemotingServer extends RemotingService {

	void start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	String getServerName();

	void setServerConfig(ServerConfig serverConfig);

	ServerConfig getServerConfig();
}
