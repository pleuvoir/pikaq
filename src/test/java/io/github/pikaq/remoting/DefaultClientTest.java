package io.github.pikaq.remoting;

import org.junit.Assert;
import org.junit.Test;

import io.github.pikaq.remoting.client.ClientConfig;
import io.github.pikaq.remoting.client.DefaultClient;

public class DefaultClientTest {

	@Test
	public void test() {

		ClientConfig clientConfig = ClientConfig.create("127.0.0.1", 8081)
			.connectTimeoutMillis(5000)
			.startFailReconnectTimes(3)
			.build();
		
		Assert.assertEquals(clientConfig.getHost(), "127.0.0.1");
		Assert.assertEquals(clientConfig.getConnectTimeoutMillis(), 5000);
		Assert.assertEquals(clientConfig.getPort(), 8081);
		Assert.assertEquals(clientConfig.getStartFailReconnectTimes(), 3);
		
		DefaultClient client = new DefaultClient("本地客户端");
		
		Assert.assertEquals(client.remoteLocation(), RemoteLocationEnum.CLIENT);
		client.setClientConfig(clientConfig);
		client.connect();
	}

}
