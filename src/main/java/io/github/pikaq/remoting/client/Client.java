package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.RemoteClientException;
import io.github.pikaq.remoting.Remoteable;

public interface Client extends Remoteable {

	void start() throws RemoteClientException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

}
