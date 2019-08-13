package io.github.pikaq.client;

import io.github.pikaq.RemotingService;
import io.github.pikaq.RunningState;
import io.github.pikaq.common.exception.RemoteClientException;

public interface RemotingClient extends RemotingService {

	void connect() throws RemoteClientException;

	void setClientConfig(ClientConfig clientConfig);

	ClientConfig getClientConfig();

	String getClientName();

	void shutdown();

	RunningState runningState();

}

