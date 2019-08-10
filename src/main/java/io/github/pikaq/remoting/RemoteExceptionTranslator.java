package io.github.pikaq.remoting;

import java.util.concurrent.TimeoutException;

public final class RemoteExceptionTranslator {

	public static RemoteSendException convertRemoteException(Throwable e) throws RemoteSendException {
		// 响应超时
		if (e instanceof TimeoutException) {
			throw new RemoteSendException("等待响应超时");
		} else {
			throw new RemoteSendException(e);
		}
	}
}
