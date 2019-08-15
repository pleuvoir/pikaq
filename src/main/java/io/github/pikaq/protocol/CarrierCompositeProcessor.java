package io.github.pikaq.protocol;

import cn.hutool.core.bean.BeanUtil;
import io.github.pikaq.ClientChannelInfo;
import io.github.pikaq.ClientChannelInfoManager;
import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.command.embed.CarrierCommand;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

@ServerSide
public class CarrierCompositeProcessor implements RemotingRequestProcessor<CarrierCommand, CarrierCommand> {

	@Override
	public CarrierCommand handler(ChannelHandlerContext ctx, CarrierCommand request) {

//		final Channel channel = ctx.channel();
//
//		String channelId = channel.id().asLongText();
//		ClientChannelInfo clientChannelInfo = new ClientChannelInfo(channel, channelId);
//		SingletonFactoy.get(ClientChannelInfoManager.class).add(channelId, clientChannelInfo);

		CarrierCommand response = new CarrierCommand();
		response.markResponse();
		// 处理后返回给对端不需要在响应
		response.setResponsible(false);
		return response;
	}
}
