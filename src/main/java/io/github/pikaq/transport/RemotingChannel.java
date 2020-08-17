package io.github.pikaq.transport;


import io.github.pikaq.InvokeCallback;
import io.github.pikaq.URL;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;
import java.net.InetSocketAddress;

public interface RemotingChannel {

  boolean open();

  void close();

  void close(int timeoutSecs);

  boolean isClosed();

  boolean isAvailable();

  RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
      throws RemotingTimeoutException, RemotingSendRequestException;

  void invokeAsync(RemotingCommand request, InvokeCallback invokeCallback)
      throws RemotingSendRequestException;

  void invokeOneWay(RemotingCommand request) throws RemotingSendRequestException;

  InetSocketAddress getLocalAddress();

  InetSocketAddress getRemoteAddress();

  URL getUrl();
}
