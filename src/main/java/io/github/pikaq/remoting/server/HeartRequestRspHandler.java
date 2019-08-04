package io.github.pikaq.remoting.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.remoting.protocol.command.PingCommand;
import io.github.pikaq.remoting.protocol.command.PongCommand;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 响应客户端发来的心跳报文
 *
 */
@ServerSide
@ChannelHandler.Sharable
public class HeartRequestRspHandler extends SimpleChannelInboundHandler<PingCommand> {

	public static final HeartRequestRspHandler INSTANCE = new HeartRequestRspHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PingCommand request) throws Exception {
		
		LOG.debug("[server]接收到客户端心跳报文：{}", request.toJSON());
		
		PongCommand command = new PongCommand();
		command.setAttachs("currentTimeMillis", System.currentTimeMillis());
		
		ctx.writeAndFlush(command);
		LOG.debug("[server]已响应客户端心跳报文，cmd：{}", command.toJSON());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOG.info("[server]异常，关闭连接。");
		ctx.channel().close();
	}

	private static final Logger LOG = LoggerFactory.getLogger(HeartRequestRspHandler.class);
}
