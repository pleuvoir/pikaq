package io.github.pikaq.remoting;

import io.github.pikaq.command.RpcRequest;
import io.github.pikaq.command.RpcResponse;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.server.ServerConfig;
import io.github.pikaq.server.SimpleServer;
import io.netty.channel.ChannelHandlerContext;

public class RpcServerTest {
	
	
	public static void main(String[] args) {
		
		SimpleServer simpleServer = new SimpleServer(ServerConfig.create(8888));
		
		simpleServer.registerHandler(55, new RemotingRequestProcessor<RpcRequest, RpcResponse>() {

			@Override
			public RpcResponse handler(ChannelHandlerContext ctx, RpcRequest request) {
				RpcResponse rpcResponse = new RpcResponse();
				rpcResponse.setPayload("hello rpc");
				return rpcResponse;
			}
		});
		
		simpleServer.start();
	}
}
