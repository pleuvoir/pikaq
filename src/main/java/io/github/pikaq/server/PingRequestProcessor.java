package io.github.pikaq.server;

import io.github.pikaq.MessageFrom;
import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.embed.PingCommand;
import io.github.pikaq.protocol.command.embed.PongCommand;
import io.netty.channel.ChannelHandlerContext;

@ServerSide
public class PingRequestProcessor implements RemotingRequestProcessor<PingCommand, PongCommand> {

	@Override
	public PongCommand handler(ChannelHandlerContext ctx, PingCommand request) {
		PongCommand command = new PongCommand();
		command.setMessageFrom(MessageFrom.SERVER);
		return command;
	}
}
