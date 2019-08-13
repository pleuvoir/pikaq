package io.github.pikaq.client;


import io.github.pikaq.Pendings;
import io.github.pikaq.common.annotation.ClientSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.protocol.RemotingRequestProcessor;
import io.github.pikaq.protocol.command.RemoteCommandFactory;
import io.github.pikaq.protocol.command.RemotingCommand;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * 客户端远程命令分发器
 * 
 * <p>
 * 所有命令使用此处理器处理
 * 
 * @author pleuvoir
 *
 */
@ClientSide
@ChannelHandler.Sharable
public class ClientRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemotingCommand> {

	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand response) throws Exception {
		
		
		
		 RemotingRequestProcessor processor = SingletonFactoy.get(RemoteCommandFactory.class).select(response.getRequestCode());

		if (processor != null) {
			logger.debug("ClientRemoteCommandtDispatcher received {}，handler={}", response.toJSON(),
					processor.getClass().getCanonicalName());
			processor.handler(ctx, response);
		} else {
			// 未找到处理器，直接标记为完成
			CompletableFuture<RemotingCommand> prevRequest = Pendings.remove(response.getMessageId());
			if (prevRequest != null) {
				prevRequest.complete(response);
			}
			logger.debug("ClientRemoteCommandtDispatcher received {}，prevRequest={}", response.toJSON(),
					prevRequest);
		}
		
	}

}