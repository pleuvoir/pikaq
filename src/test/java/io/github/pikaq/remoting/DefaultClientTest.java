package io.github.pikaq.remoting;

import org.junit.Assert;

import io.github.pikaq.remoting.client.ClientConfig;
import io.github.pikaq.remoting.client.DefaultClient;
import io.github.pikaq.remoting.exception.RemoteClientException;

public class DefaultClientTest {

	// junit会退出去 大坑
	public static void main(String[] args) throws RemoteClientException {
		ClientConfig clientConfig = ClientConfig.create("127.0.0.1", 8443)
				.connectTimeoutMillis(5000)
				.startFailReconnectTimes(3)
				.build();

		Assert.assertEquals(clientConfig.getHost(), "127.0.0.1");
		Assert.assertEquals(clientConfig.getConnectTimeoutMillis(), 5000);
		Assert.assertEquals(clientConfig.getPort(), 8443);
		Assert.assertEquals(clientConfig.getStartFailReconnectTimes(), 3);

		DefaultClient client = new DefaultClient("local_client");

		client.setClientConfig(clientConfig);
		
		Assert.assertTrue(!client.runningState().isRunning());
		client.connect();
		
		Assert.assertTrue(client.runningState().isRunning());
		
		
		client.shutdown();
		
		Assert.assertTrue(!client.runningState().isRunning());
	}

}
