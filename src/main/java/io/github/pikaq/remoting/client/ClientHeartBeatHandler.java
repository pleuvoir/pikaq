package io.github.pikaq.remoting.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ClientSide;
import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.HeartBeatRspCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 当触发系统预设的读写空闲事件时，开启定时心跳任务<br>
 * 	<b>一般而言，等待若干秒触发空闲事件+等待数秒后发送心跳报文时间之和  < 服务端空闲检测时间</b>
 * 这个类不能共享
 */
@ClientSide
public class ClientHeartBeatHandler extends SimpleChannelInboundHandler<HeartBeatRspCommand> {

	private static final Logger LOG = LoggerFactory.getLogger(ClientHeartBeatHandler.class);

	private final ClientConfig clientConfig;

	public ClientHeartBeatHandler(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			sendHeartPacketPeriodicity(ctx);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
	
	private void sendHeartPacketPeriodicity(ChannelHandlerContext ctx) {
		ctx.executor().schedule(() -> {
			if (ctx.channel().isActive()) {
				RemoteCommand request = DefaultRemoteCommandFactory.INSTANCE.newRemoteCommand(CommandCode.HEART_BEAT_REQ);
				ctx.writeAndFlush(request);
				LOG.debug("[client]发送心跳报文到对端。心跳间隔，{}s，request={}", clientConfig.getHeartbeatIntervalSeconds(),request.toJSON());
				this.sendHeartPacketPeriodicity(ctx); // 如果连接存活那么递归这个任务数秒后继续发送，不用关闭连接池
			}
		}, clientConfig.getHeartbeatIntervalSeconds(), TimeUnit.SECONDS);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRspCommand response) throws Exception {
		LOG.debug("[client]接收到服务端心跳响应。response={}", response.toJSON());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOG.info("[client]异常，关闭连接。");
		ctx.channel().close();
	}
}
