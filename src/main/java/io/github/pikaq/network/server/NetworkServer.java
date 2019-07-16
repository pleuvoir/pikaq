package io.github.pikaq.network.server;

import io.github.pikaq.network.Networkable;

public interface NetworkServer extends Networkable {

	void start(ServerConfig serverConfig);

	void shutdown(NetworkServerContext networkContext);

	void registerShutdownHooks(Thread... hooks);

	String getServerName();
}
