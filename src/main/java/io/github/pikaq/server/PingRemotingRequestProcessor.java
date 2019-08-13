package io.github.pikaq.server;

import io.github.pikaq.ClientChannelInfo;
import io.github.pikaq.ClientChannelInfoManager;
import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.PingCommand;
import io.github.pikaq.protocol.command.PongCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

@ServerSide
public class PingRemotingRequestProcessor implements RemotingRequestProcessor<PingCommand, PongCommand> {

	@Override
	public PongCommand handler(ChannelHandlerContext ctx, PingCommand request) {
		PongCommand command = new PongCommand();
		command.set("currentTimeMillis", System.currentTimeMillis());
		final Channel channel = ctx.channel();

		String channelId = channel.id().asLongText();
		ClientChannelInfo clientChannelInfo = new ClientChannelInfo(channel, channelId);
		SingletonFactoy.get(ClientChannelInfoManager.class).add(channelId, clientChannelInfo);
		return command;
	}

}
