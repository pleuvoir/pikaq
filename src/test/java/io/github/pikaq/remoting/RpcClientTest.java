package io.github.pikaq.remoting;

import io.github.pikaq.InvokeCallback;
import io.github.pikaq.RemotingFuture;
import io.github.pikaq.client.ClientConfig;
import io.github.pikaq.client.SimpleClient;
import io.github.pikaq.command.RpcRequest;
import io.github.pikaq.common.exception.RemotingSendRequestException;
import io.github.pikaq.common.exception.RemotingTimeoutException;
import io.github.pikaq.protocol.command.RemotingCommand;

public class RpcClientTest {

	public static void main(String[] args) throws RemotingSendRequestException, RemotingTimeoutException {

		//设置为0则关闭了心跳
		SimpleClient simpleClient = new SimpleClient(ClientConfig.create().heartbeatIntervalSeconds(0).build());

		String addr = "127.0.0.1:8888";
		String addr2 = "127.0.0.1:8888";

		// 批量连接多个不同的地址，连接过程中会进行重试，一般是为了预先连接而使用；如果有一个连接失败则停止
		simpleClient.connectWithRetry(addr, addr2);

		RpcRequest rpcRequest = new RpcRequest();

		// onway
		simpleClient.invokeOneway(addr, rpcRequest);

		// 同步调用
		RemotingCommand response = simpleClient.invokeSync(addr, rpcRequest, 1000);
		System.out.println(response.toJSON());

		// 异步回调
		simpleClient.invokeAsync(addr, rpcRequest, new InvokeCallback() {
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
