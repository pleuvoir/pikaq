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
		
		SimpleClient simpleClient = new SimpleClient(ClientConfig.create().build());
		
		String addr = "127.0.0.1:8888";
		
		//开启长连接，也可不开启，不开启调用会创建连接
		simpleClient.connectWithRetry(addr);
		
		RpcRequest rpcRequest = new RpcRequest();
		
		//onway
		simpleClient.invokeOneway(addr, rpcRequest);
		
		//同步调用
		RemotingCommand response = simpleClient.invokeSync(addr, rpcRequest, 1000);
		System.out.println(response.toJSON());
		
		//异步回调
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
	}

}
