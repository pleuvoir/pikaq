package io.github.pikaq.remoting.server;

import io.github.pikaq.remoting.RemotingRequestProcessor;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandlerContext;

public class PingRemotingRequestProcessor implements RemotingRequestProcessor {

	@Override
	public RemotingCommand handler(ChannelHandlerContext ctx, RemotingCommand request) {
		return null;
	}

}
