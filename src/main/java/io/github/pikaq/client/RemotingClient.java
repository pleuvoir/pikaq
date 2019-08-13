package io.github.pikaq.client;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RunningState;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public interface RemotingClient {

	void connectWithRetry(String addr) throws RemoteClientException;

	RemotingCommand invokeSync(String addr, RemotingCommand request, long timeoutMillis)
			throws RemotingTimeoutException, RemotingSendRequestException;

	void invokeAsync(String addr, RemotingCommand request, InvokeCallback invokeCallback)
			throws RemotingSendRequestException;

	void invokeOneway(String addr, RemotingCommand request) throws RemotingSendRequestException;

	ClientConfig getClientConfig();

	void shutdown();
	
	RunningState runningState();

}
