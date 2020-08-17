package io.github.pikaq.remoting;


import io.github.pikaq.URL;
import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.SimpleClient;
import io.github.pikaq.common.exception.RemoteClientException;
import org.junit.Assert;

public class SimpleClientTest {

  // junit会退出去 大坑
  public static void main(String[] args) throws RemoteClientException {
    ClientConfig clientConfig = ClientConfig.create()
        .connectTimeoutMillis(5000)
        .startFailReconnectTimes(3)
        .build();

    URL url = new URL("127.0.0.1", 8888);

    Assert.assertEquals(clientConfig.getConnectTimeoutMillis(), 5000);
    Assert.assertEquals(clientConfig.getStartFailReconnectTimes(), 3);

    SimpleClient client = new SimpleClient(clientConfig, url);

    client.connectWithRetry(url);

    //client.shutdown();

  }

}
