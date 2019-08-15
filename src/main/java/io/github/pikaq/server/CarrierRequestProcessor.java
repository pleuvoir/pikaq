package io.github.pikaq.server;

import io.github.pikaq.ClientChannelInfo;
import io.github.pikaq.ClientChannelInfoManager;
import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.CarrierCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

@SuppressWarnings("rawtypes")
@ServerSide
public class CarrierRequestProcessor implements RemotingRequestProcessor<CarrierCommand, CarrierCommand> {

	@Override
	public CarrierCommand handler(ChannelHandlerContext ctx, CarrierCommand request) {

		final Channel channel = ctx.channel();

		String channelId = channel.id().asLongText();
		ClientChannelInfo clientChannelInfo = new ClientChannelInfo(channel, channelId);
		SingletonFactoy.get(ClientChannelInfoManager.class).add(channelId, clientChannelInfo);

		CarrierCommand<String> command = CarrierCommand.buildString(true, "hello", "pikaq");
		command.setResponsible(false);

		return command;
	}
}
