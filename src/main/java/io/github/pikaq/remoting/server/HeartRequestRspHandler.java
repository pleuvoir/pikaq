package io.github.pikaq.remoting.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.remoting.CommandCode;
import io.github.pikaq.remoting.RemoteCommandFactory;
import io.github.pikaq.remoting.protocol.HeartBeatRequest;
import io.github.pikaq.remoting.protocol.HeartBeatResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 响应客户端发来的心跳报文
 *
 */
@ServerSide
@ChannelHandler.Sharable
public class HeartRequestRspHandler extends SimpleChannelInboundHandler<HeartBeatRequest> {

	public static final HeartRequestRspHandler INSTANCE = new HeartRequestRspHandler();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequest msg) throws Exception {
		LOG.debug("接收到客户端心跳报文：{}", msg.toJSON());
		HeartBeatResponse packet = (HeartBeatResponse) RemoteCommandFactory.select(CommandCode.HEART_BEAT_RSP).getPacket();
		packet.setLastTimestap(System.currentTimeMillis());
		ctx.writeAndFlush(packet);
		LOG.debug("已响应客户端心跳报文，packet：{}", packet.toJSON());
	}

	private static final Logger LOG = LoggerFactory.getLogger(HeartRequestRspHandler.class);
}
