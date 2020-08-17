package io.github.pikaq.remoting;

import com.alibaba.fastjson.JSON;
import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RemotingFuture;
import io.github.pikaq.URL;
import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.SimpleClient;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.embed.CarrierCommand;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;

public class ClientSendMessageTest {

  // junit会退出去 大坑
  public static void main(String[] args)
      throws RemoteClientException, RemotingSendRequestException, InterruptedException, RemotingTimeoutException {
    ClientConfig clientConfig = ClientConfig.create().connectTimeoutMillis(5000)
        .startFailReconnectTimes(3).build();

    Assert.assertEquals(clientConfig.getConnectTimeoutMillis(), 5000);
    Assert.assertEquals(clientConfig.getStartFailReconnectTimes(), 3);

    URL url = new URL("127.0.0.1", 8443);
    SimpleClient client = new SimpleClient(clientConfig, url);

    client.connectWithRetry(url);

    CarrierCommand request = CarrierCommand.builder().build();
    request.setResponsible(true);

    System.out.println("invokeOneWay -----------------------" + JSON.toJSONString(request));
    request.setMessage("invokeOneWay消息");
    client.invokeOneWay(url, request);
    TimeUnit.SECONDS.sleep(2);

    System.out.println("invokeSync -----------------------" + JSON.toJSONString(request));
    request.setMessage("invokeSync消息");
    client.invokeSync(url, request, 1000);

    TimeUnit.SECONDS.sleep(2);

    System.out.println("invokeAsync -----------------------");
    request.setMessage("invokeAsync消息");
    client.invokeAsync(url, request, new InvokeCallback() {

      @Override
      public void onRequestException(RemotingFuture remotingFuture) {
        System.out.println("异常了" + remotingFuture.getCause());
      }

      @Override
      public void onReceiveResponse(RemotingFuture remotingFuture) {
        System.out.println("接收到响应了：" + remotingFuture.getResponseCommand().toJSON());
      }
    });

  }
}
