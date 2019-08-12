package io.github.pikaq.remoting;

import io.github.pikaq.remoting.exception.RemotingSendRequestException;
import io.github.pikaq.remoting.exception.RemotingTimeoutException;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;

public interface RemotingService {

	RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
			throws RemotingTimeoutException, RemotingSendRequestException;

	void invokeAsync(RemotingCommand request, InvokeCallback invokeCallback) throws RemotingSendRequestException;

	void invokeOneway(RemotingCommand request) throws RemotingSendRequestException;
}
