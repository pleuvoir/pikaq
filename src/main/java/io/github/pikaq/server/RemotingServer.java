package io.github.pikaq.server;

import io.github.pikaq.protocol.RemotingRequestProcessor;

public interface RemotingServer {

	boolean start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	ServerConfig getServerConfig();
	
	@SuppressWarnings("rawtypes")
	void registerHandler(int requestCode, RemotingRequestProcessor handler);
}
