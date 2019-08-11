package io.github.pikaq.remoting;

import java.util.concurrent.TimeoutException;

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
