package io.github.pikaq.server;


import io.github.pikaq.RemotingService;

public interface RemotingServer extends RemotingService {

	void start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	void setServerConfig(ServerConfig serverConfig);

	ServerConfig getServerConfig();
}
