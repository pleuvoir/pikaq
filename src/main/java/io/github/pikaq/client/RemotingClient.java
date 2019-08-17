package io.github.pikaq.client;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RunningState;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public interface RemotingClient {

	/**
	 * 批量连接多个不同的地址，连接过程中会进行重试，一般是为了预先连接而使用；如果有一个连接失败则停止
	 */
	void connectWithRetry(String... addrs) throws RemoteClientException;

	RemotingCommand invokeSync(String addr, RemotingCommand request, long timeoutMillis)
			throws RemotingTimeoutException, RemotingSendRequestException;

	void invokeAsync(String addr, RemotingCommand request, InvokeCallback invokeCallback)
			throws RemotingSendRequestException;

	void invokeOneway(String addr, RemotingCommand request) throws RemotingSendRequestException;

	ClientConfig getClientConfig();

	void shutdown();

    RunningState runningState();

}
