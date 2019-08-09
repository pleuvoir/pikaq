package io.github.pikaq.remoting.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.CarrierCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ServerSide
@ChannelHandler.Sharable
public class ServerRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand> {

	public static final ServerRemoteCommandtDispatcher INSTANCE = new ServerRemoteCommandtDispatcher();

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand request) throws Exception {

		RemoteCommandProcessor<RemoteCommand, RemoteCommand> processor = SingletonFactoy.get(RemoteCommandFactory.class)
				.select(request.getSymbol());

		if (processor != null) {
			RemoteCommand response = processor.handler(ctx, request);
			response.setId(request.getId());
			ctx.writeAndFlush(response);
			logger.debug("[服务端分发器]命令处理结束：request={}，response={}", request.toJSON(), response.toJSON());
		} else {
			CarrierCommand<String> response = CarrierCommand.buildString(true, "empty", "OK");
			response.setId(request.getId());
			ctx.writeAndFlush(response);
			logger.debug("[服务端分发器]无处理器，返回empty：request={}，response={}", request.toJSON(), response.toJSON());
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}

}
