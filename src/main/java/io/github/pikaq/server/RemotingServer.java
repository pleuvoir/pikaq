package io.github.pikaq.server;

public interface RemotingServer {

	void start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	ServerConfig getServerConfig();
}
