package io.github.pikaq.basic;

import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.PingCommand;
import io.github.pikaq.remoting.protocol.command.PongCommand;
import io.netty.channel.ChannelHandlerContext;

public class HeartRemoteCommandProcessor implements RemoteCommandProcessor<PingCommand, PongCommand> {

	@Override
	public PongCommand handler(ChannelHandlerContext ctx, PingCommand request) {

		System.out.println("HeartRemoteCommandProcessor 进来了" + request.getId());
		return null;
	}

}
