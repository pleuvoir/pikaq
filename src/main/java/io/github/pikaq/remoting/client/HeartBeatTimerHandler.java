package io.github.pikaq.remoting.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ClientSide;
import io.github.pikaq.remoting.protocol.Packet;
import io.github.pikaq.remoting.protocol.RemoteCommand;
import io.github.pikaq.remoting.protocol.builder.DefaultPacketBuilder;
import io.github.pikaq.remoting.protocol.impl.HeartBeatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 定时心跳<br>
 * 连接建立后每隔5秒向服务端发送心跳报文，这个时间一般设置为空闲检测时间的1/3
 * 这个类不能共享
 */
@ClientSide
public class HeartBeatTimerHandler extends SimpleChannelInboundHandler<HeartBeatResponse> {

	private final ClientConfig clientConfig;

	public HeartBeatTimerHandler(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOG.info("客户端已连接，开启定时发送心跳报文任务。");
		sendHeartPacketPeriodicity(ctx);
		ctx.fireChannelActive();
	}

	private void sendHeartPacketPeriodicity(ChannelHandlerContext ctx) {
		ctx.executor().schedule(() -> {
			if (ctx.channel().isActive()) {
				
				RemoteCommand remoteCommand = RemoteCommand.createHeartbeatReq();
				Packet packet = DefaultPacketBuilder.getInstance().build(remoteCommand);
				ctx.writeAndFlush(packet);
				this.sendHeartPacketPeriodicity(ctx); // 如果连接存活那么递归这个任务 // 数秒后继续发送，不用关闭连接池
			}
		}, clientConfig.getHeartbeatIntervalSeconds(), TimeUnit.SECONDS);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponse msg) throws Exception {
		LOG.debug("接收到服务端心跳响应，current timestamp:{}", System.currentTimeMillis());
	}

	private static final Logger LOG = LoggerFactory.getLogger(HeartBeatTimerHandler.class);

}
