package io.github.pikaq.client;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RunningState;
import io.github.pikaq.URL;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public interface RemotingClient {

  void connectWithRetry(URL url) throws RemoteClientException;

  RemotingCommand invokeSync(URL url, RemotingCommand request, long timeoutMillis)
      throws RemotingTimeoutException, RemotingSendRequestException;

  void invokeAsync(URL url, RemotingCommand request, InvokeCallback invokeCallback)
      throws RemotingSendRequestException;

  void invokeOneWay(URL url, RemotingCommand request) throws RemotingSendRequestException;

  ClientConfig getClientConfig();

  void shutdown();

  RunningState runningState();

  URL getURL();

}
