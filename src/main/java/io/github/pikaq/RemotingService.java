package io.github.pikaq;


import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public interface RemotingService {

	RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
			throws RemotingTimeoutException, RemotingSendRequestException;

	void invokeAsync(RemotingCommand request, InvokeCallback invokeCallback) throws RemotingSendRequestException;

	void invokeOneway(RemotingCommand request) throws RemotingSendRequestException;
}
