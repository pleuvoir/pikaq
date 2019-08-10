package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.common.annotation.ClientSide;
import io.github.pikaq.common.util.SingletonFactoy;
import io.github.pikaq.remoting.Pendings;
import io.github.pikaq.remoting.protocol.RemoteCommandProcessor;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
import io.github.pikaq.remoting.protocol.command.RemoteCommandFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
public class ClientRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand> {

	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand response) throws Exception {
		
		RemoteCommandLifeCycleListener commandLifeCycleListener = SingletonFactoy.get(RemoteCommandLifeCycleListener.class);
		
		commandLifeCycleListener.beforeHandler(response);
		
		RemoteCommandProcessor processor = SingletonFactoy.get(RemoteCommandFactory.class).select(response.getSymbol());

		if (processor != null) {
			logger.debug("ClientRemoteCommandtDispatcher received {}，handler={}", response.toJSON(),
					processor.getClass().getCanonicalName());
			processor.handler(ctx, response);
		} else {
			// 未找到处理器，直接标记为完成
			CompletableFuture<RemoteCommand> prevRequest = Pendings.remove(response.getId());
			if (prevRequest != null) {
				prevRequest.complete(response);
			}
			logger.debug("ClientRemoteCommandtDispatcher received {}，prevRequest={}", response.toJSON(),
					prevRequest);
		}
		
		commandLifeCycleListener.afterHandler(response);
	}

}
