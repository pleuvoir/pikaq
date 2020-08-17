package io.github.pikaq.transport;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.URL;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;
import java.net.InetSocketAddress;

public class NettyRemoteChannel implements RemotingChannel {

  @Override
  public boolean open() {
    return false;
  }

  @Override
  public void close() {

  }

  @Override
  public void close(int timeoutSecs) {

  }

  @Override
  public boolean isClosed() {
    return false;
  }

  @Override
  public boolean isAvailable() {
    return false;
  }

  @Override
  public RemotingCommand invokeSync(RemotingCommand request, long timeoutMillis)
      throws RemotingTimeoutException, RemotingSendRequestException {
    return null;
  }

  @Override
  public void invokeAsync(RemotingCommand request, InvokeCallback invokeCallback)
      throws RemotingSendRequestException {

  }

  @Override
  public void invokeOneWay(RemotingCommand request) throws RemotingSendRequestException {

  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return null;
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return null;
  }

  @Override
  public URL getUrl() {
    return null;
  }
}
