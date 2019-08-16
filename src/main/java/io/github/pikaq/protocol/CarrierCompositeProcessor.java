package io.github.pikaq.protocol;

import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.protocol.command.embed.CarrierCommand;
import io.netty.channel.ChannelHandlerContext;

@ServerSide
public class CarrierCompositeProcessor implements RemotingRequestProcessor<CarrierCommand, CarrierCommand> {

	@Override
	public CarrierCommand handler(ChannelHandlerContext ctx, CarrierCommand request) {

		CarrierCommand response = new CarrierCommand();
		response.setMessage("echo:" + request.getMessage());
		response.markResponse();
		return response;
	}
}
