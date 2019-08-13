package io.github.pikaq.remoting;


import org.junit.Assert;

import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.SimpleClient;
import io.github.pikaq.common.exception.RemoteClientException;

public class SimpleClientTest {

	// junit会退出去 大坑
	public static void main(String[] args) throws RemoteClientException {
		ClientConfig clientConfig = ClientConfig.create()
				.connectTimeoutMillis(5000)
				.startFailReconnectTimes(3)
				.build();

		Assert.assertEquals(clientConfig.getConnectTimeoutMillis(), 5000);
		Assert.assertEquals(clientConfig.getStartFailReconnectTimes(), 3);

		SimpleClient client = new SimpleClient(clientConfig);

		
		Assert.assertTrue(!client.runningState().isRunning());
		client.connectWithRetry("127.0.0.1:8443");
		
		Assert.assertTrue(client.runningState().isRunning());
		
		
		//client.shutdown();
		
		//Assert.assertTrue(!client.runningState().isRunning());
	}

}
