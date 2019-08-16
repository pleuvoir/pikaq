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
		simpleClient.connectWithRetry(addr);
		
		RpcRequest rpcRequest = new RpcRequest();
		
		simpleClient.invokeOneway(addr, rpcRequest);
		
		RemotingCommand response = simpleClient.invokeSync(addr, rpcRequest, 1000);
		System.out.println(response.toJSON());
		
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
