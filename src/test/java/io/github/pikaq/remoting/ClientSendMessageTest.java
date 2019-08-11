package io.github.pikaq.remoting;

import java.util.concurrent.TimeUnit;

import io.github.pikaq.remoting.client.ClientConfig;
import io.github.pikaq.remoting.client.DefaultClient;
import io.github.pikaq.remoting.protocol.command.CarrierCommand;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;

public class ClientSendMessageTest {

	// junit会退出去 大坑
	public static void main(String[] args) throws RemoteClientException, RemotingSendRequestException, InterruptedException {
		ClientConfig clientConfig = ClientConfig.create("127.0.0.1", 8443)
				.connectTimeoutMillis(5000)
				.startFailReconnectTimes(3)
				.build();
		
		DefaultClient client = new DefaultClient("测试消息发送客户端");
		client.setClientConfig(clientConfig);
		
		client.connect();
		
		CarrierCommand<String> cmd = CarrierCommand.buildString(true, "", "");
		cmd.setResponsible(true); //设置为需要响应，否则收不到异步通知
		while (true) {
			System.out.println("send.");
			RemotingCommand rsp = client.sendRequest(cmd);
			System.out.println("同步响应 - " + rsp.toJSON());
			TimeUnit.SECONDS.sleep(1);
		}
		
	}
}
