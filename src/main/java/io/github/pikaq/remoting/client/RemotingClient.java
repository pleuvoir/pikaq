package io.github.pikaq.remoting.client;

import io.github.pikaq.remoting.RemotingService;
import io.github.pikaq.remoting.RunningState;
import io.github.pikaq.remoting.exception.RemoteClientException;

public interface RemotingClient extends RemotingService {

	void connect() throws RemoteClientException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}
