package io.github.pikaq.remoting.server;

public interface Server {

	void start();

	void shutdown();

	void registerShutdownHooks(Thread... hooks);

	String getServerName();

	void setServerConfig(ServerConfig serverConfig);

	ServerConfig getServerConfig();
}
