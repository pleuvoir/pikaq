package io.github.pikaq.remoting.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ClientSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.protocol.command.CommandCode;
import io.github.pikaq.remoting.protocol.command.PongCommand;
import io.github.pikaq.remoting.protocol.command.RemotingCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 健康检查
 * 
 * <p>
 *  当IdleStateHandler 30秒没有读事件会触发 {@link #userEventTriggered(ChannelHandlerContext, Object)} 开启向服务端发送心跳报文的定时任务。
 * <p>
 *  客户端和服务端之间双方维持着心跳，当服务端接收到PING后会回复给客户端PONG，这样双方都是健康的。当因为某种原因，服务端没有收到客户端PING，超过一定时间后会主动断开连接
 *  这会触发 {@link #channelInactive(ChannelHandlerContext)} 方法，从而进行客户端的短线重连。
 * 
 * @author pleuvoir
 *  
 */
@ClientSide
public class HealthyChecker extends SimpleChannelInboundHandler<PongCommand> {

	private static final Logger LOG = LoggerFactory.getLogger(HealthyChecker.class);

	private final Client client;

	public HealthyChecker(Client client) {
		this.client = client;
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
		ClientConfig clientConfig = client.getClientConfig();
		ctx.executor().schedule(() -> {
			if (ctx.channel().isActive()) {
				RemotingCommand request = SingletonFactoy.get(RemoteCommandFactory.class).newRemoteCommand(CommandCode.HEART_BEAT_REQ.getCode());
				ctx.writeAndFlush(request);
				LOG.debug("[client]发送心跳报文到对端。心跳间隔{}s，request={}", clientConfig.getHeartbeatIntervalSeconds(),request.toJSON());
				this.sendHeartPacketPeriodicity(ctx);
			}
		}, clientConfig.getHeartbeatIntervalSeconds(), TimeUnit.SECONDS);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, PongCommand response) throws Exception {
		LOG.debug("[client]接收到服务端心跳响应。response={}", response.toJSON());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		LOG.error("ctx close,cause:", cause);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (client.runningState().isRunning()) {
			ctx.executor().schedule(() -> {
				LOG.info("[{}] Try to reconnecting...", HealthyChecker.class.getSimpleName());
				client.shutdown();
				client.connect();
			}, 5, TimeUnit.SECONDS);
			ctx.fireChannelInactive();
		}
	}

}
