package io.github.pikaq.server;

public interface RemotingServer {

	boolean start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	ServerConfig getServerConfig();
	
	
}
