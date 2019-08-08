package io.github.pikaq.remoting.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ServerSide;
import io.github.pikaq.remoting.protocol.command.CarrierCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ServerSide
@ChannelHandler.Sharable
public class ServerRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand>{

	
	public static final ServerRemoteCommandtDispatcher INSTANCE = new ServerRemoteCommandtDispatcher();
	
	 final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand request) throws Exception {
		
		
		//TODO 暂时直接返回
		
		CarrierCommand<String> msg = CarrierCommand.buildString(true, "返回成功。", "OK");
		msg.setId(request.getId());
		ctx.writeAndFlush(msg);
		
		
	//	int symbol = request.getSymbol();
		//RemoteCommandHandler handler = DefaultRemoteCommandFactory.INSTANCE.select(symbol);
	
		
//		if (handler != null) {
//			logger.info("ServerRemoteCommandtDispatcher received {}，handler={}", request.toJSON(),
//					handler.getClass().getCanonicalName());
//			handler.handler(ctx, request);
//		} else {
//			// 未找到处理器，直接标记为完成
//			CompletableFuture<RemoteCommand> prevRequest = Pendings.remove(request.getId());
//			if (prevRequest != null) {
//				prevRequest.complete(request);
//			}
//			logger.info("ServerRemoteCommandtDispatcher received {}，prevRequest={}", request.toJSON(),
//					prevRequest);
//		}
		
	}

}
