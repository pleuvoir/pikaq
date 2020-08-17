package io.github.pikaq.remoting;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RemotingFuture;
import io.github.pikaq.URL;
import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.SimpleClient;
import io.github.pikaq.command.RpcRequest;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public class RpcClientTest {

  public static void main(String[] args)
      throws RemotingSendRequestException, RemotingTimeoutException {

    URL url = new URL("127.0.0.1", 8888);
    //设置为0则关闭了心跳
    SimpleClient simpleClient = new SimpleClient(
        ClientConfig.create().heartbeatIntervalSeconds(0).build(),
        url);

    simpleClient.connectWithRetry(url);

    RpcRequest rpcRequest = new RpcRequest();

    // oneWay
    simpleClient.invokeOneWay(url, rpcRequest);

    // 同步调用
    RemotingCommand response = simpleClient.invokeSync(url, rpcRequest, 1000);
    System.out.println(response.toJSON());

    // 异步回调
    simpleClient.invokeAsync(url, rpcRequest, new InvokeCallback() {
      @Override
      public void onRequestException(RemotingFuture remotingFuture) {
        System.err.println("onRequestException .. " + remotingFuture.getBeginTimestamp());
      }

      @Override
      public void onReceiveResponse(RemotingFuture remotingFuture) {
        System.out.println("onReceiveResponse .. " + remotingFuture.getResponseCommand());
      }
    });

    simpleClient.shutdown();
  }

}
