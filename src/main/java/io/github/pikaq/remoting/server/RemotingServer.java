package io.github.pikaq.remoting.server;

import io.github.pikaq.remoting.RemotingRequestProcessor;

public interface RemotingServer {

	void start();

	void shutdown();

	void registerProcessor(int symbol, RemotingRequestProcessor processor);

	RemotingRequestProcessor getProcessor(int symbol);

	void registerShutdownHooks(Thread... hooks);

	String getServerName();

	void setServerConfig(ServerConfig serverConfig);

	ServerConfig getServerConfig();
}
