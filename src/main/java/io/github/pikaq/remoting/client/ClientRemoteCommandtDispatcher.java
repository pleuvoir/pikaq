package io.github.pikaq.remoting.client;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.pikaq.remoting.Pendings;
import io.github.pikaq.remoting.protocol.RemoteCommandHandler;
import io.github.pikaq.remoting.protocol.command.DefaultRemoteCommandFactory;
import io.github.pikaq.remoting.protocol.command.RemoteCommand;
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
public class ClientRemoteCommandtDispatcher extends SimpleChannelInboundHandler<RemoteCommand> {

	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RemoteCommand response) throws Exception {
		
		System.out.println("1111111111111111");
		
		int symbol = response.getSymbol();
		RemoteCommandHandler handler = DefaultRemoteCommandFactory.INSTANCE.select(symbol);

		if (handler != null) {
			logger.info("ClientRemoteCommandtDispatcher received {}，handler={}", response.toJSON(),
					handler.getClass().getCanonicalName());
			handler.handler(ctx, response);
		} else {
			// 未找到处理器，直接标记为完成
			CompletableFuture<RemoteCommand> prevRequest = Pendings.remove(response.getId());
			if (prevRequest != null) {
				prevRequest.complete(response);
			}
			logger.info("ClientRemoteCommandtDispatcher received {}，prevRequest={}", response.toJSON(),
					prevRequest);
		}
	}

}
