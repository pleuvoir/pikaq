package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.Remoteable;

public interface Client extends Remoteable {

	void connect();

	void reconnect();

	void disconnect();
	
	void setClientConfig(ClientConfig clientConfig);
	
	ClientConfig getClientConfig();
	
	String getClientName();
	
	void shutdown();
	
}
