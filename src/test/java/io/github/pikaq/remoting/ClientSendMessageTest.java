package io.github.pikaq.remoting;


import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.KeepAliveClient;
import io.github.pikaq.common.exception.RemoteClientException;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.protocol.command.CarrierCommand;

import java.util.concurrent.TimeUnit;

public class ClientSendMessageTest {

	// junit会退出去 大坑
	public static void main(String[] args) throws RemoteClientException, RemotingSendRequestException, InterruptedException {
		ClientConfig clientConfig = ClientConfig.create("127.0.0.1", 8443)
				.connectTimeoutMillis(5000)
				.startFailReconnectTimes(3)
				.build();
		
		KeepAliveClient client = new KeepAliveClient("测试消息发送客户端");
		client.setClientConfig(clientConfig);
		
		client.connect();
		
		CarrierCommand<String> request = CarrierCommand.buildString(true, "", "");
		
		
		while (true) {
			System.out.println("send.");
			client.invokeOneway(request);
			TimeUnit.SECONDS.sleep(1);
		}
		
	}
}
