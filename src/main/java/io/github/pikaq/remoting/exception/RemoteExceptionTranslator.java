package io.github.pikaq.remoting.exception;

import java.util.concurrent.TimeoutException;

import io.github.pikaq.remoting.exception.RemotingSendRequestException;

public final class RemoteExceptionTranslator {

	public static RemotingSendRequestException convertRemoteException(Throwable e) throws RemotingSendRequestException {
		// 响应超时
		if (e instanceof TimeoutException) {
			throw new RemotingSendRequestException("等待响应超时");
		} else {
			throw new RemotingSendRequestException(e);
		}
	}
}
