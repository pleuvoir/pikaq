package io.github.pikaq.remoting;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.github.pikaq.command.UserReqCommand;
import io.github.pikaq.remoting.client.ClientConfig;
import io.github.pikaq.remoting.client.DefaultClient;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;

public class ClientSendMessageTest {

	// junit会退出去 大坑
	public static void main(String[] args) throws RemoteClientException {
		ClientConfig clientConfig = ClientConfig.create("127.0.0.1", 8443)
				.connectTimeoutMillis(5000)
				.startFailReconnectTimes(3)
				.build();
		

		DefaultClient client = new DefaultClient("测试消息发送客户端");
		client.setClientConfig(clientConfig);
		
		client.connect();
		UserReqCommand cmd = new UserReqCommand();
		cmd.setName("pleuvoir");
		
		CompletableFuture<RemoteCommand> sendAsyncRequest = client.sendAsyncRequest(cmd);
		
		try {
			RemoteCommand remoteCommand = sendAsyncRequest.get();
			System.out.println("响应 - " + remoteCommand.toJSON());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		
		RemoteCommand rsp = client.sendRequest(cmd);
		System.out.println("同步响应 - " + rsp.toJSON());
	}
}
